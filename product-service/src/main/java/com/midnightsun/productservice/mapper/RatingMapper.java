package com.midnightsun.productservice.mapper;

import com.midnightsun.productservice.model.Rating;
import com.midnightsun.productservice.service.dto.RatingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "product.id", source = "productId")
    Rating toEntity(RatingDTO ratingDTO);

    @Mapping(target = "productId", source = "product.id")
    RatingDTO toDTO(Rating rating);
}
