package com.onlinemobilestore.dto;

import com.onlinemobilestore.entity.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductDetailDto {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String description;
    private double priceNew;
    private String nameColor;
    private Storage storage;
    private Category category;
    private String nameBrand;
    private List<Image> images;
    private List<Discount> discounts;
    private List<Preview> previews;
}
