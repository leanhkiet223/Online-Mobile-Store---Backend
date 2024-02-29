package com.onlinemobilestore.API;

import com.onlinemobilestore.services.serviceImpl.OrderDetailServiceImpl;
import com.onlinemobilestore.services.serviceImpl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class OrderAPI {
    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    OrderDetailServiceImpl orderDetailService;
    @PostMapping ("/order/create/{idUser}")
    public ResponseEntity<?> createOrder(@PathVariable("idUser") Integer idUser){
        return ResponseEntity.ok(orderService.createOrder(idUser));
    }

    @GetMapping("/orders/{idUser}")
    public ResponseEntity<?> getOrders(@PathVariable("idUser") Integer idUser){
        return ResponseEntity.ok(orderService.getOrdersByUserID(idUser));
    }

    @PostMapping ("/order/cancelOrder/{idOrder}")
    public ResponseEntity<?> cancelOrder(@PathVariable("idOrder") Integer id){
        orderService.cancelOrder(id);
        return ResponseEntity.ok("");
    }

    @GetMapping("/order-details/{idOrder}")
    public ResponseEntity<?> getOrderDetailsByOrderID(@PathVariable("idOrder") Integer id){
        return ResponseEntity.ok(orderDetailService.getOrderDetailsById(id));
    }

}
