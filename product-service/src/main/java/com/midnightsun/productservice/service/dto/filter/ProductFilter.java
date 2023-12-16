package com.midnightsun.productservice.service.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilter {
    public String name;
    public BigDecimal priceFrom;
    public BigDecimal priceTo;
    public Long quantityFrom;
    public Long quantityTo;
    public Long categoryId;
}
