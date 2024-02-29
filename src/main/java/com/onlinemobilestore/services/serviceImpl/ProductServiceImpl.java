package com.onlinemobilestore.services.serviceImpl;


import com.onlinemobilestore.dto.*;
import com.onlinemobilestore.entity.Product;
import com.onlinemobilestore.repository.ProductRepository;
import com.onlinemobilestore.services.servicelnterface.ProductService;
//import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
//    @Autowired
//    private ModelMapper modelMapper;

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
//    @Override
//    public List<ProducDto> findAll() {
//        List<Product> products = productRepository.findAll();
//        List<ProducDto> productDtos = products.stream()
//                .map(product -> {
//                  ProducDto producDto =  modelMapper.map(product, ProducDto.class);
//                  if(!product.getImages().isEmpty()){
//                      String image = product.getImages().get(0).getImageUrl();
//                      producDto.setImage(image);
//                  }
//                  return producDto;
//                })
//                .collect(Collectors.toList());
//        return productDtos;
//    }
//
//    @Override
//    public List<ProductBrand> findByTrademarkId(int id) {
//        List<Product> products = productRepository.findByTrademarkId(id);
//        List<ProductBrand> productBrands = products.stream().map(product -> {
//            ProductBrand productBrand = modelMapper.map(product, ProductBrand.class);
//            productBrand.setPriceNew(product.getPrice() * (100-product.getPercentDiscount()));
//            productBrand.setNameColor(product.getColor().getColor());
//            productBrand.setRate(product.getPreviews().stream()
//                .mapToDouble(Preview::getRate)
//                        .average()
//                        .orElse(0.0));
//            productBrand.setImage(product.getImages().stream()
//                    .map(Image::getImageUrl)
//                    .collect(Collectors.toList()));
//            productBrand.setDiscount(product.getDiscounts().stream()
//                    .map(discount -> {
//                        String nameDiscount = discount.getName();
//                        Date date = discount.getExpirationDate();
//                        return nameDiscount + String.valueOf(date);
//                    })
//                    .collect(Collectors.toList()));
//
//            return productBrand;
//        }).collect(Collectors.toList());
//        return productBrands;
//    }

    @Override
    public List<ProductDTO> getProductByBrandId(Integer id) {
        List<ProductDTO> products = productRepository.findByTrademarkId(id)
                .stream()
                .map(ProductDTO::mapperProduct)
                .collect(Collectors.toList());
        return products;
    }


    @Override
    public List<ProductSaleDTO> getProductByDiscount() {
        List<Product> products = productRepository.findAll();
        List<ProductSaleDTO> productSale = products.stream()
                .filter(product -> product.getDiscounts() != null && !product.getDiscounts().isEmpty() && product.getQuantity() > 0)
                .flatMap(product -> product.getDiscounts().stream().map(discount ->
                        new ProductSaleDTO(
                                product.getId() ,
                                product.getName(),
                                product.getPrice(),
                                product.getPrice() * (1 - discount.getPercent() / 100),
                                Optional.ofNullable(product.getImages())
                                        .filter(images -> !images.isEmpty())
                                        .map(images -> images.get(0).getImageUrl())
                                        .orElse(null),
                                product.getQuantity(),
                                product.isState(),
                                discount.getPercent(),
                                discount.getExpirationDate()
                        )
                ))
                .collect(Collectors.toList());
        return productSale;
    }

    @Override
    public List<ProductDTO> getProducts() {
        List<ProductDTO> products = productRepository.findAll().stream()
                .map(ProductDTO::mapperProduct
                ).collect(Collectors.toList());
        return products;
    }

    @Override
    public ProductDetailsDTO getProductById(Integer id) {
        Product product = productRepository.findById(id).get();
        if(product != null){
            return ProductDetailsDTO.mapperProduct(product);
        }
        return null;
    }


}
