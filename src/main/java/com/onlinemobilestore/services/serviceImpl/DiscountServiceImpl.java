package com.onlinemobilestore.services.serviceImpl;

import com.onlinemobilestore.dto.DiscountDTO;
import com.onlinemobilestore.entity.Cart;
import com.onlinemobilestore.entity.CartDetail;
import com.onlinemobilestore.entity.Discount;
import com.onlinemobilestore.repository.CartDetailRepository;
import com.onlinemobilestore.repository.CartRepository;
import com.onlinemobilestore.repository.DiscountRepository;
import com.onlinemobilestore.repository.ProductRepository;
import com.onlinemobilestore.services.servicelnterface.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements DiscountService {
    private DiscountRepository discountRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartDetailRepository cartDetailRepository;

    @Override
    public List<Discount> findAll() {
        try {
            return discountRepository.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    @Override
    public Discount createDiscount(Discount discount) {
        try {
            discount.setId(null);
            return  discountRepository.save(discount);
        } catch (DataIntegrityViolationException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Discount updateDiscount(int discountId, Discount discount) {
        Optional<Discount> optionalDiscount = discountRepository.findById(discountId);
        if(optionalDiscount.isPresent()){

            return  discountRepository.save(discount);
        }else {
            return null;
        }
    }

    @Override
    public String deleteDiscount(int discountId) {
        Optional<Discount> optionalDiscount = discountRepository.findById(discountId);
        if(optionalDiscount.isPresent()){
            discountRepository.deleteById(discountId);
            return "delete success";
        }else {
            return "delete fail";
        }

    }

    @Query("SELECT d FROM Discount d WHERE d.product.id = :productId")
    List<Discount> findDiscountIdByProductId(@Param("productId") Integer productId) {
        return null;
    }

    @Override
    public Discount findDiscountByName(String name) {
        Discount discount =  discountRepository.findDiscountByName(name);
        if(discount.isActive() == true && discount !=null){
            return discount;
        }
        return null;
    }
    @Override
    public List<DiscountDTO> findDiscountByUserId(Integer userID) {
        Cart cart = cartRepository.findByUserId(userID);
        if (cart != null) {
            List<DiscountDTO> discounts = cart.getCartDetails().stream()
                    .map(CartDetail::getProduct)
                    .filter(product -> product.getDiscounts() != null && !product.getDiscounts().isEmpty() && product.getQuantity() > 0)
                    .flatMap(product -> product.getDiscounts().stream().map(DiscountDTO::mapperDiscount))
                    .collect(Collectors.toList());

            return discounts;
        } else {
            return Collections.emptyList();
        }
    }



    public DiscountServiceImpl(DiscountRepository discountRepository){
        this.discountRepository = discountRepository;
    }


}
