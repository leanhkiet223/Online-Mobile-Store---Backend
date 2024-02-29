package com.onlinemobilestore.repository;

import com.onlinemobilestore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByTrademarkId(int id);
    List<Product> findByName(String name);
    List<Product> findByStorage_ReadOnlyMemoryValue(int readOnlyMemoryValue);
    List<Product> findByNameContaining(String partialName);
    List<Product> findByNameContainingIgnoreCase(String searchKey);
}
