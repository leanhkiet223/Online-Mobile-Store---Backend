package com.onlinemobilestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    private String email;
    private String phoneNumber;
    private String fullName;
    private String password;
    private String confirmPassword;
}
