package com.onlinemobilestore.services.serviceImpl;

import com.onlinemobilestore.dto.*;
import com.onlinemobilestore.entity.*;
import com.onlinemobilestore.repository.*;
import com.onlinemobilestore.services.servicelnterface.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartDetailRepository cartDetailRepository;

    public List<OrderForUserDTO> getOrdersForUser(int userId) {
        try {
            List<Order> orders = orderRepository.findByUserId(userId);

            List<OrderForUserDTO> orderForUser = orders.stream()
                    .map(order -> new OrderForUserDTO(order.getId(),
                            order.getTotal(),
                            order.getOrderDetails().stream()
                                    .mapToInt(OrderDetail::getQuantity)
                                    .sum(),
                            order.getState(),
                            order.getCreateDate()))
                    .collect(Collectors.toList());
            return orderForUser;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public ProductInOrderDTO createOrderAndOrderDetailByUserIdAndByProductId(int userId, int productId) {
        try {
            Order order = new Order();
            User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
            Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
            order.setUser(user);
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setPrice(product.getPrice() * (1 - (product.getPercentDiscount()/ 100.0)));
            orderDetail.setQuantity(1);
            ProductInOrderDTO productInOrderDTO = new ProductInOrderDTO();
            productInOrderDTO.setColor(product.getColor());
            productInOrderDTO.setNameProduct(product.getName());
            productInOrderDTO.setProductId(productId);
            productInOrderDTO.setPrice(product.getPrice() * (1 - (product.getPercentDiscount()/ 100.0)));
            List<String> imageUrl = product.getImages().stream().map(Image::getImageUrl).collect(Collectors.toList());
            productInOrderDTO.setImages(imageUrl);
            productInOrderDTO.setQuantity(1);
            productInOrderDTO.setCreateDate(new Date());
            productInOrderDTO.setState(0);
            order.setTotal(orderDetail.getPrice()*orderDetail.getQuantity());
            order.setState(0);
            orderRepository.save(order);
            orderDetailRepository.save(orderDetail);
            return productInOrderDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error creating order and order detail", e);
        }
    }
    private OrderRepository orderRepository;
    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Integer idUser) {
        try {
            Cart cart = cartRepository.findByUserId(idUser);
            List<CartDetail> cartDetails = cartDetailRepository.findByCartId(cart.getId());
            if (cart != null && !cartDetails.isEmpty()) {
                Order order = createOrderFromCart(cart);
                if (order != null) {
                    order.getOrderDetails().forEach(orderDetail -> {
                        try {
                            Product product = orderDetail.getProduct();
                            product.setQuantity(product.getQuantity() - orderDetail.getQuantity());
                            productRepository.save(product);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    cleanupCart(cart);
                    return order;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Order createOrderFromCart(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setState(1);
        order.setCreateDate(new Date());

        List<CartDetail> cartDetails = cartDetailRepository.findByCartId(cart.getId());
        if (cartDetails != null){

            double total = cartDetails.stream()
                    .map(CartDetailsDTO::mapperCartDetails)
                    .mapToDouble(CartDetailsDTO::getSubtotal)
                    .sum();
            order.setTotal(total);
            orderRepository.save(order);
            List<OrderDetail> orderDetails = cartDetails.stream()
                    .map(CartDetailsDTO::mapperCartDetails)
                    .map(cartDetail -> {
                        OrderDetail orderDetail = new OrderDetail();
                        orderDetail.setOrder(order);

                        Product product = productRepository.findById(cartDetail.getProductId())
                                .orElseGet(() -> null);
                        orderDetail.setProduct(product);
                        orderDetail.setQuantity(cartDetail.getQuantity());
                        orderDetail.setPrice(cartDetail.getNewPrice());
                        orderDetailRepository.save(orderDetail);
                        return orderDetail;
                    })
                    .collect(Collectors.toList());

            order.setOrderDetails(orderDetails);
            return order;
        }
        return null;

    }

    private void cleanupCart(Cart cart) {
        List<CartDetail> cartDetails = cartDetailRepository.findByCartId(cart.getId());
        if(cartDetails != null){
            cartDetails.forEach(cartDetail -> {
                cartDetailRepository.delete(cartDetail);
            });
            cartRepository.save(cart);
        }
    }

    @Override
    public List<OrderDTO> getOrdersByUserID(Integer id) {
        List<Order> orders = orderRepository.findByUserId(id);
        if(orders != null){
            List<OrderDTO> orderDTOS = orders.stream().map(OrderDTO::mapperOrder).collect(Collectors.toList());
            return orderDTOS;
        }
       return Collections.emptyList();
    }

    @Override
    public void cancelOrder(Integer idOrder) {
        Order order = orderRepository.findById(idOrder).get();
        if(order != null){
            order.setState(0);
            orderRepository.save(order);
        }
    }

}
