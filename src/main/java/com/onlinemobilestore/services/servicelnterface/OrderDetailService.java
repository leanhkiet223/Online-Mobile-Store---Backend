package com.onlinemobilestore.services.servicelnterface;

import com.onlinemobilestore.dto.OrderDetailDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderDetailService {
    List<OrderDetailDTO> getOrderDetailsById(Integer orderID);

}
