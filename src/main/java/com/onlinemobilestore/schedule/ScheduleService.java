package com.onlinemobilestore.schedule;

import com.onlinemobilestore.entity.Cart;
import com.onlinemobilestore.entity.CartDetail;
import com.onlinemobilestore.repository.CartDetailRepository;
import com.onlinemobilestore.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartDetailRepository cartDetailRepository;

    @Scheduled(fixedRate = 18000000)
    private void cleanCartSession() {
        try {
            List<Cart> cartList = cartRepository.findAll();
            List<Cart> cartRemove = new ArrayList<>();
            List<CartDetail> cartDetailRemove = new ArrayList<>();
            cartList.stream().forEach((cart -> {
                if (cart.getUser() == null && !isCartExpired(cart.getUpdateDate())) {
                    cartDetailRemove.addAll(cart.getCartDetails());
                    cartRemove.add(cart);
                }
            }));
            cartRepository.deleteAll(cartRemove);
            cartDetailRepository.deleteAll(cartDetailRemove);
            System.out.println("Xóa Cart định kỳ thành công");
        } catch (Exception e) {
            System.err.println("Xóa Cart định kỳ lỗi");
            e.printStackTrace();
        }
    }

    private boolean isCartExpired(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 3);
        Date expirationDate = calendar.getTime();
        return expirationDate.compareTo(new Date()) < 0;
    }
}