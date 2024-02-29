package com.onlinemobilestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class DTOPreview {
    String content;
    Double rate;
    String user;
    Date createDate;
    String product;
}
