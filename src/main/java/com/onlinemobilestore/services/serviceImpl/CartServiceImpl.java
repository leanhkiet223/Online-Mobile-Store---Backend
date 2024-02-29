package com.onlinemobilestore.services.serviceImpl;

import com.onlinemobilestore.dto.CartDetailsDTO;
import com.onlinemobilestore.entity.*;
import com.onlinemobilestore.repository.CartDetailRepository;
import com.onlinemobilestore.repository.CartRepository;
import com.onlinemobilestore.repository.ProductRepository;
import com.onlinemobilestore.repository.UserRepository;
import com.onlinemobilestore.services.servicelnterface.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;

    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Autowired
    HttpSession session;


    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


    @Override
    public Cart addCart(int userId) {
        Cart cartByUser = cartRepository.findByUserId(userId);
        if (cartByUser == null) {
            Cart cart = new Cart();
            User user = (User) session.getAttribute("userSession");
            cart.setUser(user);
            return cartRepository.save(cart);
        }
        return cartByUser;
    }

    @Override
    public List<CartDetailsDTO> getCartByUserId(Integer id) {
        Cart cart = cartRepository.findByUserId(id);
        if (cart != null && !cart.getCartDetails().isEmpty() &&  cart.getCartDetails() !=null) {
            List<CartDetailsDTO> cartDetails = cart.getCartDetails()
                    .stream()
                    .map(CartDetailsDTO::mapperCartDetails)
                    .collect(Collectors.toList());
            return cartDetails;
        } else {
            return Collections.emptyList();
        }
    }
    @Override
    public CartDetailsDTO addToCart(Integer idUser, Integer idProduct) {
        User user = userRepository.findById(idUser).orElse(null);
        Cart cart = cartRepository.findByUserId(idUser);
        Product product = productRepository.findById(idProduct).orElse(null);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
            CartDetail cartDetail = new CartDetail();
            cartDetail.setCart(cart);
            cartDetail.setQuantity(1);
            product.getDiscounts().stream().mapToDouble(Discount::getPercent).sum();
            cartDetail.setProduct(product);
            cartDetailRepository.save(cartDetail);
            return CartDetailsDTO.mapperCartDetails(cartDetail);
        }

        Optional<CartDetail> existingCartDetail = cart.getCartDetails().stream()
                .filter(detail -> detail.getProduct().getId().equals(idProduct))
                .findFirst();
        if (existingCartDetail.isPresent()) {
            CartDetail cartDetail = existingCartDetail.get();
            int newQuantity = cartDetail.getQuantity() + 1;
            if (newQuantity <= product.getQuantity()) {
                cartDetail.setQuantity(newQuantity);
                cartDetailRepository.save(cartDetail);
                return CartDetailsDTO.mapperCartDetails(cartDetail);
            } else {
            }
        } else {
            CartDetail cartDetail = new CartDetail();
            cartDetail.setCart(cart);
            cartDetail.setProduct(product);
            cartDetail.setQuantity(1);
            cartDetailRepository.save(cartDetail);
            return CartDetailsDTO.mapperCartDetails(cartDetail);
        }
        return null;
    }
}




