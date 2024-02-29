package com.onlinemobilestore.API;

import com.onlinemobilestore.entity.Category;
import com.onlinemobilestore.entity.Order;
import com.onlinemobilestore.entity.OrderDetail;
import com.onlinemobilestore.repository.OrderDetailRepository;
import com.onlinemobilestore.repository.OrderRepository;
import com.onlinemobilestore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminOrderAPI {
    @Autowired
    OrderRepository ordDAO;
    @Autowired
    OrderDetailRepository ordDDAO;
    @Autowired
    UserRepository useDAO;

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = ordDAO.findAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer id) {
        Optional<Order> order = ordDAO.findById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/order/orderDetail/{idOrder}")
    public ResponseEntity<List<OrderDetail>> getOrderDetailByOrderId(@PathVariable Integer idOrder) {
        Optional<Order> order = ordDAO.findById(idOrder);
        if (order.isPresent()) {
           List<OrderDetail> orderDetails = order.get().getOrderDetails();
            return ResponseEntity.ok(orderDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        order.setId(null);
        Order createdOrder = ordDAO.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable int id, @RequestBody Order updatedOrder) {
        Optional<Order> existingOrder = ordDAO.findById(id);
        if (existingOrder.isPresent()) {
            updatedOrder.setId(id);
            Order savedOrder = ordDAO.save(updatedOrder);
            return ResponseEntity.ok(savedOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        Optional<Order> order = ordDAO.findById(id);
        if (order.isPresent()) {
            ordDAO.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
