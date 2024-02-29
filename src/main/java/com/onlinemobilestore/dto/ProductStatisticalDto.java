package com.onlinemobilestore.dto;

import com.onlinemobilestore.entity.Product;
import lombok.Data;

@Data
public class ProductStatisticalDto {
    private final int id;
    private final String name;
    private final String color;
    private final String image;
    private final Integer quantity;

    public ProductStatisticalDto(Product product, Integer quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        this.id = product.getId();
        this.name = product.getName();
        this.color = product.getColor() != null ? product.getColor().getColor() : null;
        this.image = initializeImage(product);
        this.quantity = quantity;
    }

    private String initializeImage(Product product) {
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            return product.getImages().get(0).getImageUrl();
        }
        return null;
    }

}

