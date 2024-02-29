package com.onlinemobilestore.API;

import com.onlinemobilestore.services.MailService;
import com.onlinemobilestore.dto.RegisterDTO;
import com.onlinemobilestore.entity.User;
import com.onlinemobilestore.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin
public class RegisterRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterDTO userDTO) {
        try {
            System.out.println("Register");
            if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
                return "Password and ConfirmPassword not match";
            }
            Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());
            if (optionalUser.isPresent()) {
                return "User with this email already exists";
            }
            if (userRepository.existsUserByPhoneNumber(userDTO.getPhoneNumber())) {
                return "User with this phoneNumber already exists";
            }
            User user = new User();
            user.setEmail(userDTO.getEmail());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setAddress(null);
            user.setBirthday(null);
            user.setFullName(userDTO.getFullName());
            String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
            user.setPassword(hashedPassword);
            user.setCreateDate(new Date());
            user.setActive(true);
            user.setRoles("USER");
            user.setAvatar(null);
            userRepository.save(user);
            mailService.sendWelcomeMail(userDTO.getEmail());
            return "Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error during user registration";
        }
    }
}
