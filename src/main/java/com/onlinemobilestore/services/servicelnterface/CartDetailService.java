package com.onlinemobilestore.services.servicelnterface;

import com.onlinemobilestore.dto.CartDetailsDTO;
import org.springframework.stereotype.Component;

@Component
public interface CartDetailService {
    CartDetailsDTO updateQuantity(Integer idUser,Integer idCartDetail, Integer newQuanity);

    void deleteCartItem(Integer id);

}
