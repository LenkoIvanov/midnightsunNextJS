package com.midnightsun.productservice.mapper;

import com.midnightsun.productservice.model.Product;
import com.midnightsun.productservice.model.Rating;
import com.midnightsun.productservice.service.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ProductMapperDecorator implements ProductMapper {

    @Autowired
    private ProductMapper mapper;

    @Override
    public Product toEntity(ProductDTO productDTO) {
        return mapper.toEntity(productDTO);
    }

    @Override
    public ProductDTO toDTO(Product product) {
        ProductDTO dto = mapper.toDTO(product);
        Double totalScore = product.getRatingList().stream()
                .map(Rating::getScore)
                .reduce(0d, Double::sum);

        Double avgScore = totalScore / product.getRatingList().size();
        if (avgScore.isNaN()) {
            dto.setRatingScore(0d);
        } else {
            dto.setRatingScore(totalScore / product.getRatingList().size());
        }


        return dto;
    }
}
