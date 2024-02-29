package com.onlinemobilestore.dto;

import com.onlinemobilestore.entity.Color;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@CrossOrigin
public class OrderForUserDTO {

    private int id;
    private Double total;
    private int quantity;
    private int state;
    private Date createDate;
}