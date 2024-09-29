package com.dhruv.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

// records have getters provided by default remember that ...
public record ProductRequest(

        Integer id,

        @NotNull(message = "Product name is required")
        String name,

        @NotNull(message = "Product description is required")
        String description,

        @Positive(message = "available qty should be positive")
        double availableQuantity,
        // good for storing data like money

        @Positive(message = "price should be posiitve")
        BigDecimal price,

        @NotNull(message = "product category is required ")
        Integer categoryId
) {
}
