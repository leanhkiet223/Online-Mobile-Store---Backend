package com.onlinemobilestore.dto;

import com.onlinemobilestore.entity.CartDetail;
import com.onlinemobilestore.entity.Discount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailsDTO {
    private Integer id;
    private Integer productId;
    private String image;
    private String name;
    private String trademark;
    private double oldPrice;
    private double newPrice;
    private Integer quantity;
    private double subtotal;
    private Integer quantityProduct;

    public static CartDetailsDTO mapperCartDetails(CartDetail cartDetail){
        CartDetailsDTO cartDetailsDTO = new CartDetailsDTO();
        cartDetailsDTO.setId(cartDetail.getId());
        cartDetailsDTO.setProductId(cartDetail.getProduct().getId());
        cartDetailsDTO.setImage(cartDetail.getProduct().getImages().get(0).getImageUrl());
        cartDetailsDTO.setName(cartDetail.getProduct().getName());
        cartDetailsDTO.setTrademark(cartDetail.getProduct().getTrademark().getName());
        cartDetailsDTO.setOldPrice(cartDetail.getProduct().getPrice());
        double percent = cartDetail.getProduct().getDiscounts().stream().mapToDouble(Discount::getPercent).sum();
        double newPrice = cartDetail.getProduct().getPrice() * (1 - percent / 100);
        cartDetailsDTO.setNewPrice(newPrice);
        cartDetailsDTO.setQuantity(cartDetail.getQuantity());
        cartDetailsDTO.setSubtotal(cartDetail.getQuantity() * newPrice);
        cartDetailsDTO.setQuantityProduct(cartDetail.getProduct().getQuantity());
        return cartDetailsDTO;
    }

}
