package com.midnightsun.productservice.mapper;

import com.midnightsun.productservice.model.Review;
import com.midnightsun.productservice.service.dto.ReviewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "product.id", source = "productId")
    Review toEntity(ReviewDTO reviewDTO);

    @Mapping(target = "productId", source = "product.id")
    ReviewDTO toDTO(Review review);
}
