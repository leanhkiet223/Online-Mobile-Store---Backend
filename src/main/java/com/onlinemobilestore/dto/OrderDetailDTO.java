package com.onlinemobilestore.dto;

import com.onlinemobilestore.entity.Discount;
import com.onlinemobilestore.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private Integer id;
    private Integer productId;
    private String image;
    private String color;
    private String ram;
    private String name;
    private String trademark;
    private double oldPrice;
    private double newPrice;
    private Integer quantity;


    public static OrderDetailDTO mapperOrderDetail(OrderDetail orderDetail){
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setId(orderDetail.getId());
        orderDetailDTO.setProductId(orderDetail.getProduct().getId());
        orderDetailDTO.setImage(orderDetail.getProduct().getImages().get(0).getImageUrl());
        orderDetailDTO.setColor(orderDetail.getProduct().getColor().getColor());
        orderDetailDTO.setRam(orderDetail.getProduct().getStorage().getRandomAccessMemoryValue()+orderDetail.getProduct().getStorage().getRandomAccessMemoryUnit());
        orderDetailDTO.setName(orderDetail.getProduct().getName());
        orderDetailDTO.setTrademark(orderDetail.getProduct().getTrademark().getName());
        orderDetailDTO.setOldPrice(orderDetail.getProduct().getPrice());
        orderDetailDTO.setNewPrice(orderDetail.getPrice());
        orderDetailDTO.setQuantity(orderDetail.getQuantity());
        return orderDetailDTO;
    }
}
