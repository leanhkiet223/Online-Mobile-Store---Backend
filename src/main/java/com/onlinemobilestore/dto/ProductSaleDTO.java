package com.onlinemobilestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ProductSaleDTO {
    private Integer id;
    private String name;
    private double oldPrice;
    private double newPrice;
    private String image;
    private int quantity;
    private boolean state;
    private double percent;
    private Date expirationDate;
}
