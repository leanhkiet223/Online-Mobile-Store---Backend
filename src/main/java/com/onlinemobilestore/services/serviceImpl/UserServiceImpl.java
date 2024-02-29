package com.onlinemobilestore.services.serviceImpl;


import com.onlinemobilestore.services.MailService;

import com.onlinemobilestore.dto.AccountDTO;

import com.onlinemobilestore.entity.User;
import com.onlinemobilestore.repository.UserRepository;
import com.onlinemobilestore.services.servicelnterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private MailService mailService;

    @Autowired
     private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public AccountDTO getAccountById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            AccountDTO accountDTO = AccountDTO.mapperUser(user.get());
            return accountDTO;
        }
        return null;
    }

//    @Override
//    public String add(User user) {
//        if(userRepository.existsByEmail(user.getEmail())){
//            return "exist email";
//        }else if(userRepository.existsUserByPhoneNumber(user.getPhoneNumber())){
//            return "exist phoneNumber";
//        }
//        String encodedPassword = this.passwordEncoder.encode(user.getPassword());
//        User user = new User(user);
//        user.setPassword(encodedPassword);
//        userRepository.save(user);
//        return  "add " + userDto.getFullName() ;
//    }
public void processForgotPassword(String email) {
    User user = userRepository.findUserByEmail(email);
    if (user == null) {
        throw new NotFoundException("User not found");
    }
    String token = generateRandomToken();
    user.setResetPasswordToken(token);
    user.setResetTokenGenerated(true);
    userRepository.save(user);
    // Send email to the user with the reset password link
    String resetPasswordLink = token; // http://localhost:8080/reset-password?token=
    String emailContent = "Please click the following link to reset your password: " + resetPasswordLink;
    mailService.sendMail(email, "Reset Password", emailContent);
}
    public class NotFoundException extends RuntimeException {
        public NotFoundException(String message) {
            super(message);
        }
    }
    private String generateRandomToken() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder token = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            token.append(characters.charAt(random.nextInt(characters.length())));
        }
        return token.toString();
    }

}
