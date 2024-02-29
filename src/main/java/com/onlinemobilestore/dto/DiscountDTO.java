package com.onlinemobilestore.dto;

import com.onlinemobilestore.entity.Discount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDTO {
    private Integer id;
    private Integer productId;
    private String name;
    private double percent;
    private boolean active;
    private String nameProduct;

    public static DiscountDTO mapperDiscount(Discount discount){
        DiscountDTO discountDTO = new DiscountDTO();
        discountDTO.setId(discount.getId());
        discountDTO.setProductId(discount.getProduct().getId());
        discountDTO.setName(discount.getName());
        discountDTO.setPercent(discount.getPercent());
        discountDTO.setActive(discount.isActive());
        discountDTO.setNameProduct(discount.getProduct().getName());
        return discountDTO;
    }

}
