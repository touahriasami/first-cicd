package com.amigoscode.product;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record NewProductRequest(
        @NotBlank
        @Size(
                min = 2,
                max = 50,
                message = "Name must be between 2 and 50 characters"
        )
        String name,
        @Size(
                min = 5,
                max = 500,
                message = "Description must be between 5 and 500 characters"
        )
        String description,
        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.1", message = "Price must be greater than 0.1")
        BigDecimal price,

        @NotNull(message = "Price is required")
        @Min(value = 1, message = "Min Stock Level is 1")
        Integer stockLevel,
        String imageUrl
) {
}
