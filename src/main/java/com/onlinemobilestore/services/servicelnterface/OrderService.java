package com.onlinemobilestore.services.servicelnterface;

import com.onlinemobilestore.dto.OrderDTO;
import com.onlinemobilestore.entity.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderService {
    Order createOrder(Integer idUser);

    List<OrderDTO>  getOrdersByUserID(Integer id);

    void cancelOrder(Integer idOrder);
}
