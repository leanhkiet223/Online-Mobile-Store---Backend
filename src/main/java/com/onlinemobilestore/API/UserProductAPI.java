package com.onlinemobilestore.API;

import com.onlinemobilestore.dto.DTOSearch;
import com.onlinemobilestore.dto.ProductDTO;
import com.onlinemobilestore.dto.ProductDetailsDTO;
import com.onlinemobilestore.entity.Category;
import com.onlinemobilestore.entity.Product;
import com.onlinemobilestore.repository.CategoryRepository;
import com.onlinemobilestore.repository.ProductRepository;
import com.onlinemobilestore.repository.TrademarkRepository;
import com.onlinemobilestore.services.serviceImpl.ProductServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api")
@RestController
@CrossOrigin("*")
public class UserProductAPI {
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    TrademarkRepository trademarkRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/products/brand/{id}")
    public ResponseEntity<?> getProductByBrand(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(productService.getProductByBrandId(id));
    }

    @GetMapping("/products/sale")
    public ResponseEntity<?> getProductSale() {
        return ResponseEntity.ok(productService.getProductByDiscount());
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/product-detail/{id}")
    public ResponseEntity<?> getProductDetail(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/product-detail/category/{idProduct}")
    public ResponseEntity<?> getProductDetailCategory(@PathVariable("idProduct") Integer idProduct) {
        Product pro = productRepository.findById(idProduct).get();
        Category cate = pro.getCategory();
        List<ProductDetailsDTO> list = new ArrayList<>();
        cate.getProducts().stream().forEach(product -> {
            list.add(productService.getProductById(product.getId()));
        });
        return ResponseEntity.ok(list);
    }

    @GetMapping("/getData/search")
    public ResponseEntity<?> getSearch() {
        List<DTOSearch> data = new ArrayList<>();
        categoryRepository.findAll().stream().forEach((cate -> {
            data.add(new DTOSearch(cate.getProducts().get(0).getId(),cate.getName()));
        }));
        return ResponseEntity.ok(data);
    }
    @GetMapping("/products/search/{searchKey}")
    public ResponseEntity<?> getProductSearch(@PathVariable("searchKey") String searchKey) {
        List<Product> productList = productRepository.findByNameContainingIgnoreCase(searchKey);
        List<ProductDTO> list = new ArrayList<>();
        productList.stream().forEach((product -> list.add(ProductDTO.mapperProduct(product))));
        return ResponseEntity.ok(list);
    }

}


