package com.onlinemobilestore.dto;

import com.onlinemobilestore.entity.Color;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInOrderDTO {
    private int id;
    private Color color;
    private int productId;
    private Date createDate;
    private String nameProduct;
    private double price;
    private List<String> images;
    private int quantity;
    private int state;

}