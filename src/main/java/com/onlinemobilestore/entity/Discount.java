package com.onlinemobilestore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "discount")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double percent;
    @JoinColumn(name = "active")
    private boolean active;
    @JoinColumn(name = "special")
    private boolean special;
    private Date expirationDate;
    private Date createDate;
    private Date modifiedDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String description;

}

