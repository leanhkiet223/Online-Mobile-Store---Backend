package com.onlinemobilestore.dto;

import com.onlinemobilestore.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private int id;
    private String name;
    private double oldPrice;
    private double newPrice;
    private String image;
    private int quantity;
    private boolean state;
    private boolean discount;
    private String rom;
    private String categoryName;

    public static ProductDTO mapperProduct(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setOldPrice(product.getPrice());
        productDTO.setNewPrice(product.getPrice() * (1 - product.getPercentDiscount() / 100));
        productDTO.setImage( product.getImages() != null  && !product.getImages().isEmpty() ? product.getImages().get(0).getImageUrl() : null);
        productDTO.setQuantity(product.getQuantity());
        productDTO.setState(product.isState());
        productDTO.setDiscount(product.getDiscounts() != null && !product.getDiscounts().isEmpty() ? true : false);
        productDTO.setRom( product.getStorage().getReadOnlyMemoryValue()+product.getStorage().getReadOnlyMemoryUnit());
        productDTO.setCategoryName(product.getCategory().getName());
        return productDTO;
    }
}
