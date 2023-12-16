package com.midnightsun.productservice.mapper;

import com.midnightsun.productservice.model.Product;
import com.midnightsun.productservice.service.dto.ProductDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { CategoryMapper.class, RatingMapper.class, ReviewMapper.class })
@DecoratedWith(ProductMapperDecorator.class)
public interface ProductMapper {

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "ratingList", ignore = true)
    @Mapping(target = "reviewList", ignore = true)
    Product toEntity(ProductDTO productDTO);

    @Mapping(target = "reviewList", source = "reviewList")
    ProductDTO toDTO(Product product);
}
