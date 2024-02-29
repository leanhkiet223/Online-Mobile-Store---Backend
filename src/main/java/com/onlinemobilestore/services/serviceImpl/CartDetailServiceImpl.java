package com.onlinemobilestore.services.serviceImpl;

import com.onlinemobilestore.dto.CartDetailsDTO;
import com.onlinemobilestore.entity.Cart;
import com.onlinemobilestore.entity.CartDetail;
import com.onlinemobilestore.repository.CartDetailRepository;
import com.onlinemobilestore.repository.CartRepository;
import com.onlinemobilestore.services.servicelnterface.CartDetailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartDetailServiceImpl implements CartDetailService {
    private CartDetailRepository cartDetailRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    HttpSession session;

    public CartDetailServiceImpl (CartDetailRepository cartDetailRepository){
        this.cartDetailRepository = cartDetailRepository;
    }


    @Override
    public CartDetailsDTO updateQuantity(Integer idUser, Integer idCartDetail, Integer newQuantity) {
       Cart cart = cartRepository.findByUserId(idUser);
        if (cart != null  && !cart.getCartDetails().isEmpty()) {
            for (CartDetail cartDetail : cart.getCartDetails()) {
                if (cartDetail.getId().equals(idCartDetail)) {
                    cartDetail.setQuantity(newQuantity);
                    cartDetailRepository.save(cartDetail);
                    return CartDetailsDTO.mapperCartDetails(cartDetail);
                }
            }
        }
        return null;
    }

    @Override
    public void deleteCartItem(Integer id) {
        CartDetail cartDetail = cartDetailRepository.findById(id).get();
        if(cartDetail != null){
            cartDetailRepository.delete(cartDetail);
        }
    }

}
