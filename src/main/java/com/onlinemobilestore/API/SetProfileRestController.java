package com.onlinemobilestore.API;

import com.onlinemobilestore.dto.UserProfileDTO;
import com.onlinemobilestore.entity.User;
import com.onlinemobilestore.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@CrossOrigin
@RestController

public class SetProfileRestController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    UserRepository userDAO;

    @PostMapping("/set-profile")
    public UserProfileDTO setProfile(@RequestBody UserProfileDTO userProfileDTO) {
        Optional<User> optionalUser = userDAO.findById(userProfileDTO.getUserId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFullName(userProfileDTO.getFullName());
            user.setPhoneNumber(userProfileDTO.getPhoneNumber());
            user.setAddress(userProfileDTO.getAddress());
            user.setBirthday(userProfileDTO.getBirthDay());
            user.setEmail(userProfileDTO.getEmail());
            user.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
            user.setActive(true);
            userDAO.save(user);

            UserProfileDTO userProfileResponse = new UserProfileDTO(
                    user.getId(),
                    user.getFullName(),
                    user.getPhoneNumber(),
                    user.getAddress(),
                    user.getBirthday(),
                    user.getEmail()
            );

            return userProfileResponse;
        }
        return null;
    }

}


