package com.onlinemobilestore.dto;

import lombok.Data;

@Data
public class SignUpDto {
    private String email;
    private String fullName;
    private String phoneNumber;
    private String password;
}