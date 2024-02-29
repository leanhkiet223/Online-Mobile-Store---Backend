package com.onlinemobilestore.configuration;

import com.onlinemobilestore.entity.Cart;
import com.onlinemobilestore.entity.CartDetail;
import com.onlinemobilestore.entity.User;
import com.onlinemobilestore.repository.CartDetailRepository;
import com.onlinemobilestore.repository.CartRepository;
import com.onlinemobilestore.repository.UserRepository;
import com.onlinemobilestore.services.serviceImpl.CartDetailServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartDetailServiceImpl cartDetailService;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartDetailRepository cartDetailRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.setAttribute("currentUser", authentication.getName());
        User user = userRepository.findUserByEmail( authentication.getName());
        if (user!=null){
            session.setAttribute("userSession", user);
            Cart cart = cartRepository.findByUserId(user.getId());
            if(cart != null){
                List<CartDetail> cartDetails = cartDetailRepository.findByCartId(cart.getId());
                session.setAttribute("myCartItems",cartDetails);
            }
        }
        response.sendRedirect("/homepage");
    }
}
