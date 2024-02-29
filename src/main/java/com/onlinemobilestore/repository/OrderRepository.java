package com.onlinemobilestore.repository;

import com.onlinemobilestore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(int userId);

    Order findByIdAndUserId(Integer id, Integer userId);
}
