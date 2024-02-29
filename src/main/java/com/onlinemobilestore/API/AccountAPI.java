package com.onlinemobilestore.API;


import com.onlinemobilestore.services.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@CrossOrigin("*")
public class AccountAPI {
    @Autowired
    UserServiceImpl userService;
    @GetMapping("/account/{id}")
    public ResponseEntity<?> getAccount(@PathVariable("id") Integer id){
        return ResponseEntity.ok(userService.getAccountById(id));
    }
}
