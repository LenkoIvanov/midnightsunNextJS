package com.midnightsun.productservice.service;

import com.midnightsun.productservice.mapper.ProductMapper;
import com.midnightsun.productservice.model.Product;
import com.midnightsun.productservice.repository.CategoryRepository;
import com.midnightsun.productservice.repository.ProductRepository;
import com.midnightsun.productservice.service.dto.ProductDTO;
import com.midnightsun.productservice.service.dto.filter.ProductFilter;
import com.midnightsun.productservice.service.specification.ProductSpec;
import com.midnightsun.productservice.web.exception.HttpBadRequestException;
import com.midnightsun.productservice.web.exception.HttpNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Page<ProductDTO> getAll(Pageable pageable, ProductFilter filter) {
        log.debug("Request to get all PRODUCTS");
        Specification<Product> spec = ProductSpec.filterBy(filter);
        return productRepository.findAll(spec, pageable)
                .map(productMapper::toDTO);
    }

    @Transactional
    public Page<ProductDTO> getProductsOrderedByAverageRatingScore(Pageable pageable) {
        log.debug("Request to get PRODUCTS ordered by average rating score");
        return productRepository.findTopNProducts(pageable)
                .map(productMapper::toDTO);
    }

    @Transactional
    public ProductDTO getOne(Long id) {
        log.debug("Request to get PRODUCT by ID: {}", id);

        return productRepository.findById(id)
                .map(productMapper::toDTO)
                .orElse(null);
    }

    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save PRODUCT: {}", productDTO);

        if (productDTO.getId() != null) throw new HttpBadRequestException(HttpBadRequestException.ID_NON_NULL);
        if (!categoryRepository.existsById(productDTO.getCategory().getId())) throw new HttpNotFoundException("Category not found!");

        final var product = productMapper.toEntity(productDTO);
        final var savedProduct = productRepository.save(product);

        return productMapper.toDTO(savedProduct);
    }

    public ProductDTO update(ProductDTO productDTO) {
        log.debug("Request to update PRODUCT: {}", productDTO);

        if (productDTO.getId() == null) throw new HttpBadRequestException(HttpBadRequestException.ID_NULL);
        if (productDTO.getCategory() == null || !categoryRepository.existsById(productDTO.getCategory().getId())) throw new HttpNotFoundException("Category not found!");
        if (!productRepository.existsById(productDTO.getId())) throw new HttpNotFoundException("Product not found");

        final var product = productMapper.toEntity(productDTO);
        final var savedProduct = productRepository.save(product);

        return productMapper.toDTO(savedProduct);
    }

    public void delete(Long id) {
        log.debug("Request to delete PRODUCT with ID: {}", id);
        productRepository.deleteById(id);
    }

    public Map<Long, Long> checkProductsAvailability(Map<Long, Long> productsIdQuantityMap) {
        List<ProductDTO> productsToSave = new ArrayList<>();

        for (Map.Entry<Long, Long> entry : productsIdQuantityMap.entrySet()) {
            final var product = getOne(entry.getKey());

            if (product == null || (product.getQuantity() - entry.getValue()) < 0) {
                //TODO: Return a map with all products quantities and where quantity is insufficient return -1
                //Then later iterate thru this map and write which products are insufficient
                return null;
            } else {
                product.setQuantity(product.getQuantity() - entry.getValue());
                productsToSave.add(product);
            }
        }

        productsToSave.stream()
                .map(productMapper::toEntity)
                .forEach(productRepository::save);

        return productsIdQuantityMap;
    }
}
