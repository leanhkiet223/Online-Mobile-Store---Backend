package com.onlinemobilestore.services.servicelnterface;

import com.onlinemobilestore.dto.CartDetailsDTO;
import com.onlinemobilestore.entity.Cart;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CartService {
    Cart addCart(int userId);

    List<CartDetailsDTO> getCartByUserId(Integer id);

    CartDetailsDTO addToCart(Integer idUser, Integer idProduct);

}
