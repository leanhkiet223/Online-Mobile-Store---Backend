package com.onlinemobilestore.API;

import com.onlinemobilestore.services.serviceImpl.CartDetailServiceImpl;
import com.onlinemobilestore.services.serviceImpl.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CartAPI {
    @Autowired
    CartServiceImpl cartService;
    @Autowired
    CartDetailServiceImpl cartDetailService;
    @GetMapping("/cart/{id}")
    public ResponseEntity<?> getCartByUserId(@PathVariable("id") Integer id){
        return ResponseEntity.ok(cartService.getCartByUserId(id));
    }

    @PostMapping("/cart/update-quantity/{idUser}/{IdCartItem}/{newQuantity}")
    public ResponseEntity<?> updateQuantity(
            @PathVariable("idUser") Integer idUser,
            @PathVariable("IdCartItem") Integer cartItemId
            , @PathVariable("newQuantity")  Integer newQuantity){
        return ResponseEntity.ok(cartDetailService.updateQuantity(idUser,cartItemId,newQuantity));
    }

    @DeleteMapping("/cart/delete-cartitem/{id}")
    public ResponseEntity<?> deleteCartItem(
            @PathVariable("id") Integer cartItemId){
        cartDetailService.deleteCartItem(cartItemId);
        return ResponseEntity.ok("");
    }

    @PostMapping("/cart/add-to-cart/{idUser}/{idProduct}")
    public ResponseEntity<?> addToCart(
            @PathVariable("idUser") Integer idUser,
            @PathVariable("idProduct") Integer productId
            ){
        return ResponseEntity.ok(cartService.addToCart(idUser,productId));
    }
}
