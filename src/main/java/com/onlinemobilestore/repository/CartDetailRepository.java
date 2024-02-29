package com.onlinemobilestore.repository;

import com.onlinemobilestore.entity.Cart;
import com.onlinemobilestore.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail,Integer> {


    CartDetail findByProductId(int id);
    CartDetail findByProductIdAndCartId(int productId, int id);
    List<CartDetail> findByCartId(int id);


}
