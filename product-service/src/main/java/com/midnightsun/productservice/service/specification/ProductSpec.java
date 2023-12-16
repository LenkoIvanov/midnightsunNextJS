package com.midnightsun.productservice.service.specification;

import com.midnightsun.productservice.model.Product;
import com.midnightsun.productservice.service.dto.filter.ProductFilter;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpec {
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String QUANTITY = "quantity";
    private static final String CATEGORY = "category";
    private static final String ID = "id";

    private ProductSpec() {}

    public static Specification<Product> filterBy(ProductFilter productFilter) {
        return Specification
                .where(hasName(productFilter.getName()))
                .and(hasPriceGreaterThan(productFilter.getPriceFrom()))
                .and(hasPriceLessThan(productFilter.getPriceTo()))
                .and(hasQuantityGreaterThan(productFilter.getQuantityFrom()))
                .and(hasQuantityLessThan(productFilter.getQuantityTo()))
                .and(hasCategoryId(productFilter.getCategoryId()));
    }

    private static Specification<Product> hasName(String name) {
        return (root, query, cb) -> name == null || name.isEmpty() ? cb.conjunction() : cb.equal(root.get(NAME), name);
    }

    private static Specification<Product> hasPriceGreaterThan(BigDecimal priceFrom) {
        return (root, query, cb) -> priceFrom == null ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get(PRICE), priceFrom);
    }

    private static Specification<Product> hasPriceLessThan(BigDecimal priceTo) {
        return (root, query, cb) -> priceTo == null ? cb.conjunction() : cb.lessThanOrEqualTo(root.get(PRICE), priceTo);
    }

    private static Specification<Product> hasQuantityGreaterThan(Long quantityFrom) {
        return (root, query, cb) -> quantityFrom == null ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get(QUANTITY), quantityFrom);
    }

    private static Specification<Product> hasQuantityLessThan(Long quantityTo) {
        return (root, query, cb) -> quantityTo == null ? cb.conjunction() : cb.lessThanOrEqualTo(root.get(QUANTITY), quantityTo);
    }

    private static Specification<Product> hasCategoryId(Long categoryId) {
        return (root, query, cb) -> categoryId == null ? cb.conjunction() : cb.equal(root.get(CATEGORY).get(ID), categoryId);
    }
}
