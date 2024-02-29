package com.onlinemobilestore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name = "user")
@CrossOrigin
public class User {
    public User() {
    }
    public User(int id, String phoneNumber, String address, String fullName, Date birthday, String email,
            String password, String avatar, String roles, Date createDate, boolean isActive, List<Order> orders, List<Preview> previews) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.fullName = fullName;
        this.birthday = birthday;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.roles = roles;
        this.createDate = createDate;
        this.active = isActive;
        this.orders = orders;
        this.previews = previews;
    }
    public void setResetTokenGenerated(Boolean resetTokenGenerated) {
        this.isResetTokenGenerated = resetTokenGenerated != null ? resetTokenGenerated : false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String phoneNumber;
    private String address;
    private String fullName;
    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date birthday;
    private String email;
    private String password;
    private String avatar;
    private String roles = "USER";
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "active")
    private boolean active;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Column(name = "reset_token_generated")
    private Boolean  isResetTokenGenerated;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Order> orders;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Preview> previews;


}
