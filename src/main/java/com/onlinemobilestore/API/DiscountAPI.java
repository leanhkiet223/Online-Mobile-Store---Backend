package com.onlinemobilestore.API;

import com.onlinemobilestore.services.serviceImpl.DiscountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"*"})
public class DiscountAPI {
    @Autowired
    DiscountServiceImpl discountService;
    @GetMapping("/discount/{idUser}")
    public ResponseEntity<?> getDiscount(@PathVariable("idUser") Integer idUser){
        return ResponseEntity.ok(discountService.findDiscountByUserId(idUser));
    }
}
