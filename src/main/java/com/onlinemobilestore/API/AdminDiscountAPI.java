package com.onlinemobilestore.API;

import com.onlinemobilestore.entity.Discount;
import com.onlinemobilestore.repository.DiscountRepository;
import com.onlinemobilestore.services.serviceImpl.DiscountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDiscountAPI {
    @Autowired
    DiscountServiceImpl discountService;

    @Autowired
    DiscountRepository discountRepository;

    @GetMapping("/discounts")
    public ResponseEntity<List<Discount>> getDiscounts(){
        return ResponseEntity.ok(discountService.findAll());
    }

    @PostMapping("/discount")
    public ResponseEntity<Discount> createDiscount(@RequestBody Discount discount) {
        try {
            Integer productId = discount.getProduct().getId();
            if (discountRepository.existsByProductId(productId)) {
                return ResponseEntity.status(HttpStatus.IM_USED).body(null);
            }
            return ResponseEntity.ok(discountService.createDiscount(discount));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PutMapping("/discount/{discountId}")
    public ResponseEntity<Discount> updateDiscount(@PathVariable("discountId") int discountId, @RequestBody Discount discount){
        try {
            return ResponseEntity.ok(discountService.updateDiscount(discountId, discount));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(discountService.updateDiscount(discountId, discount));
        }
    }
    @DeleteMapping("/discount/{discountId}")
    public ResponseEntity<String> deleteDiscount(@PathVariable("discountId") int discountId){
        try {
            return ResponseEntity.ok(discountService.deleteDiscount(discountId));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail");
        }
    }
}
