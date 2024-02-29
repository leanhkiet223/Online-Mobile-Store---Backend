package com.onlinemobilestore.services.servicelnterface;

import com.onlinemobilestore.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductService {
    List<ProductDTO> getProductByBrandId(Integer id);
    List<ProductSaleDTO> getProductByDiscount();
    List<ProductDTO> getProducts();

    ProductDetailsDTO getProductById(Integer id);

}
