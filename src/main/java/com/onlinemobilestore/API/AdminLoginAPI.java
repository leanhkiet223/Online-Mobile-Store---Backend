package com.onlinemobilestore.API;

import com.onlinemobilestore.filter.jwt.JwtUtils;
import com.onlinemobilestore.entity.User;
import com.onlinemobilestore.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
public class AdminLoginAPI {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;

    @Autowired
    HttpServletRequest request;
    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/loginAdmin/{email}/{password}")
    public ResponseEntity<User> authenticateAdmin( @PathVariable("email") String email, @PathVariable("password") String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email.trim(), password.trim()));
            if (authentication.getAuthorities().toString().contains("ROLE_ADMIN")) {
                String jwt = jwtUtils.generateJwtToken(authentication);
                User user = userRepository.findUserByEmail(email);
                user.setPassword(jwt);
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            return ResponseEntity.badRequest().build();
        }
    }

}