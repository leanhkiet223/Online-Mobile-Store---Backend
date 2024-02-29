package com.onlinemobilestore.dto;

import com.onlinemobilestore.entity.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailsDTO {
    private Integer id;
    private String name;
    private double oldPrice;
    private double newPrice;
    private int quantity;
    private String trademark;
    private String description;
    private boolean state;
    private String ram;
    private String rom;
    private String color;
    private List<String> images;
    private boolean discount;
    private List<Preview> previews;

    public static ProductDetailsDTO mapperProduct(Product product){
        ProductDetailsDTO productDetailsDTO = new ProductDetailsDTO();
        productDetailsDTO.setId(product.getId());
        productDetailsDTO.setName(product.getName());
        productDetailsDTO.setOldPrice(product.getPrice());
        productDetailsDTO.setNewPrice(product.getPrice() * (1 - product.getPercentDiscount() / 100));
        productDetailsDTO.setQuantity(product.getQuantity());
        productDetailsDTO.setTrademark(product.getTrademark().getName());
        productDetailsDTO.setDescription(product.getDescription());
        productDetailsDTO.setState(product.isState());
        productDetailsDTO.setRam(product.getStorage().getRandomAccessMemoryValue()+product.getStorage().getRandomAccessMemoryUnit());
        productDetailsDTO.setRom(product.getStorage().getReadOnlyMemoryValue()+product.getStorage().getReadOnlyMemoryUnit());
        productDetailsDTO.setColor(product.getColor().getColor());
        productDetailsDTO.setImages(product.getImages().stream().map(Image::getImageUrl).collect(Collectors.toList()));
        productDetailsDTO.setDiscount(product.getDiscounts() != null && !product.getDiscounts().isEmpty() ? true : false);
        productDetailsDTO.setPreviews(product.getPreviews());
        return productDetailsDTO;
    }

}
