package com.onlinemobilestore.dto;

import com.onlinemobilestore.entity.Color;
import com.onlinemobilestore.entity.Discount;
import com.onlinemobilestore.entity.Image;
import com.onlinemobilestore.entity.Storage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductBrand {
    private int id;
    private String name;
    private double price;
    private double priceNew;
    private List<String> discount;
    private List<String> image;
    private String nameColor;
    private Storage storage;
    private double rate;
}