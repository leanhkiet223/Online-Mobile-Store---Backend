package com.onlinemobilestore.services.servicelnterface;

import com.onlinemobilestore.dto.DiscountDTO;
import com.onlinemobilestore.entity.Discount;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DiscountService {
    public List<Discount> findAll();
    Discount createDiscount(Discount discount);

    Discount updateDiscount(int discountId, Discount discount);

    String deleteDiscount(int discountId);

    Discount findDiscountByName(String name);
    List<DiscountDTO> findDiscountByUserId(Integer userId);

}
