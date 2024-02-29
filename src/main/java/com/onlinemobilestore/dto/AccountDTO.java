package com.onlinemobilestore.dto;

import com.onlinemobilestore.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private Integer id;
    private String phoneNumber;
    private String address;
    private String fullName;
    private Date birthday;
    private String email;
    private String avatar;
    private Date createDate;
    private boolean active;
    private String password;

    public static AccountDTO mapperUser(User user){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(user.getId());
        accountDTO.setPhoneNumber(user.getPhoneNumber());
        accountDTO.setAddress(user.getAddress());
        accountDTO.setFullName(user.getFullName());
        accountDTO.setBirthday(user.getBirthday());
        accountDTO.setEmail(user.getEmail());
        accountDTO.setAvatar(user.getAvatar());
        accountDTO.setCreateDate(user.getCreateDate());
        accountDTO.setActive(user.isActive());
        accountDTO.setPassword(user.getPassword());
        return accountDTO;
    }
}