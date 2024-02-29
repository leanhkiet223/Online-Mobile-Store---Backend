package com.onlinemobilestore.repository;

import com.onlinemobilestore.entity.Trademark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrademarkRepository extends JpaRepository<Trademark,Integer> {
}
