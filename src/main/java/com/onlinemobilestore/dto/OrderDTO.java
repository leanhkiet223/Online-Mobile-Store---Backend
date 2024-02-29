package com.onlinemobilestore.dto;


import com.onlinemobilestore.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer id;
    private double total;
    private Date createDate;
    private int state;
    private String address;
    private String name;
    private String phoneNumber;

    public static OrderDTO mapperOrder(Order order){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setTotal(order.getTotal());
        orderDTO.setCreateDate(order.getCreateDate());
        orderDTO.setState(order.getState());
        orderDTO.setAddress(order.getUser().getAddress());
        orderDTO.setName(order.getUser().getFullName());
        orderDTO.setPhoneNumber(order.getUser().getPhoneNumber());
        return orderDTO;
    }

}
