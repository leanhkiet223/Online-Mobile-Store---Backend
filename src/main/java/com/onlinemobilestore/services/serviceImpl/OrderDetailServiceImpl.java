package com.onlinemobilestore.services.serviceImpl;

import com.onlinemobilestore.dto.OrderDetailDTO;
import com.onlinemobilestore.entity.*;
import com.onlinemobilestore.repository.*;
import com.onlinemobilestore.services.servicelnterface.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    private OrderDetailRepository orderDetailRepository;
    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }
    @Autowired
    OrderRepository orderRepository;


    @Override
    public List<OrderDetailDTO> getOrderDetailsById(Integer orderID) {
        Order order = orderRepository.findById(orderID).get();
        if(order != null && !order.getOrderDetails().isEmpty()){
            List<OrderDetailDTO> orderDetails = order.getOrderDetails()
                    .stream().map(OrderDetailDTO::mapperOrderDetail)
                    .collect(Collectors.toList());
            return orderDetails;
        }
        return null;
    }
}
