package com.onlinemobilestore.API;

import com.onlinemobilestore.dto.OrderForUserDTO;
import com.onlinemobilestore.entity.*;

import com.onlinemobilestore.repository.*;
import com.onlinemobilestore.services.serviceImpl.DiscountServiceImpl;
import com.onlinemobilestore.services.serviceImpl.OrderServiceImpl;

import com.onlinemobilestore.services.serviceImpl.StatisticalServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class UserAdminRestController {

    @Autowired
    private DiscountServiceImpl discountService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userDao;
    @Autowired
    CategoryRepository categoryDAO;
    @Autowired
    DiscountRepository discountDAO;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    OrderDetailRepository orderDetailRepository;

//    @PutMapping("/account/{id}/deactivate")
//    public dataUser deactivateUser(@PathVariable Integer id) {
//        Optional<User> userOptional = userDao.findById(id);
//        if (userOptional.isEmpty()) {
//            List<User> data = userDao.findAll();
//            List<UserData> userDataList = new ArrayList<>();
//            for (User user : data) {
//
//                userDataList.add(new UserData(user.getId(), user.getEmail(), user.getAddress(), user.getFullName(),
//                        user.getAvatar(), user.getBirthday(), user.getPhoneNumber(), user.getCreateDate(), user.isActive(), user.getRoles()));
//            }
//            return new dataUser("User not found", userDataList);
//        }
//
//        User users = userOptional.get();
//        users.setActive(false);
//        userDao.save(users);
//
//        List<User> data = userDao.findAll();
//        List<UserData> userDataList = new ArrayList<>();
//        for (User user : data) {
//
//            userDataList.add(new UserData(user.getId(), user.getEmail(), user.getAddress(), user.getFullName(),
//                    user.getAvatar(), user.getBirthday(), user.getPhoneNumber(), user.getCreateDate(), user.isActive(), user.getRoles()));
//        }
//
//        String message = "User with id " + id + " has been deactivated";
//        return new dataUser(message, userDataList);
//    }

    //Begin
    @GetMapping("/getTypePhone")
    public List<String> getTypePhone() {
        return categoryDAO.getAllName();
    }

    @GetMapping("/getProductsDiscount")
    public List<DataProductHasDiscount> getAllProductDiscount() {
        List<Product> data = productRepository.findAll();
        List<DataProductHasDiscount> jsonData = new ArrayList<>();
        DiscountBrief maxPrecent;

        for (Product pro : data) {

            List<Discount> listDiscount = pro.getDiscounts();
            System.out.println(data.size());
            System.out.println("ffjfj");
            if (listDiscount.size() > 0) {
                List<DiscountBrief> discountBriefs = new ArrayList<>();
                listDiscount.stream().forEach(dis -> discountBriefs.add(new DiscountBrief(dis.getName(), dis.getPercent(), dis.getExpirationDate(), dis.getCreateDate(), dis.isActive())));
                maxPrecent = discountBriefs.get(0);
                for (int i = 0; i < discountBriefs.size(); i++) {
                    if (discountBriefs.get(i).getPercent() > maxPrecent.getPercent()) {
                        maxPrecent = discountBriefs.get(i);
                    }
                }
                jsonData.add(new DataProductHasDiscount(pro.getId(), pro.getImages(), null, pro.getName(), pro.getPrice(),
                        Math.floor(pro.getPrice() - (pro.getPrice() * pro.getPercentDiscount() / 100)), pro.getPercentDiscount(), discountBriefs));
            }

        }
        return jsonData;
    }

    @GetMapping("/getDiscounts")
    public List<Discount> getDiscounts() {
        List<Discount> data = discountDAO.findAll();
        return data;
    }

    @GetMapping("/getProducts/{name}")
    public List<DataProductHasDiscount> getProductByName(@PathVariable("name") String name) {
        List<Product> data = productRepository.findByNameContaining(name);
        List<Product> listProduct = new ArrayList<>();

        List<DataProductHasDiscount> jsonData = new ArrayList<>();
        for (Product pro : data) {
            List<Storage> storages = new ArrayList<>();
            listProduct = pro.getCategory().getProducts();
            System.out.println(listProduct.size());
            listProduct.stream().forEach(p -> {
                storages.add(p.getStorage());
            });
            List<DiscountBrief> discountBriefs = new ArrayList<>();
            pro.getDiscounts().stream().forEach(dis -> discountBriefs.add(new DiscountBrief(dis.getName(), dis.getPercent(), dis.getExpirationDate(), dis.getCreateDate(), dis.isActive())));
            jsonData.add(new DataProductHasDiscount(pro.getId(), pro.getImages(), storages, pro.getName(),
                    pro.getPrice(), (pro.getPrice() - pro.getPrice() * (pro.getPercentDiscount() / 100)), pro.getPercentDiscount(), discountBriefs));
        }
        return jsonData;
    }


    @CrossOrigin("*")
    @PostMapping("/signin/{email}/{password}")
    public User authenticateUser(@PathVariable("email") String email, @PathVariable("password") String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email.trim(), password.trim()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userRepository.findUserByEmail(email);
            return user;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/getInformationBrand/{id}")
    public List<InformationBrand> getInformationBrand(@PathVariable("id") int id) {
        System.out.println(id);
        List<Product> productList = productRepository.findByTrademarkId(id);
        System.out.println(productList.size());
        List<Product> productListStore = new ArrayList<Product>();

        List<InformationBrand> informationBrandList = new ArrayList<>();
        float maxPercent = 0;
        for (Product data : productList) {
            List<Storage> storages = new ArrayList<Storage>();
            productListStore = data.getCategory().getProducts();
            productListStore.stream().forEach(productDetail -> {
                storages.add(productDetail.getStorage());
            });
            if (data.getDiscounts().size() > 0) {
                maxPercent = (float) data.getDiscounts().get(0).getPercent();
            }
            InformationBrand informationBrand = new InformationBrand(
                    data.getId(),
                    data.getName(),
                    data.getPrice(),
                    Math.floor((data.getPrice() * (100 - data.getPercentDiscount()) / 100)),
                    data.getQuantity(),
                    data.getDescription(),
                    data.getImages(),
                    data.getCreateDate(),
                    data.getModifiedDate(),
                    data.getPercentDiscount(),
                    storages
            );
            informationBrandList.add(informationBrand);
        }
        return informationBrandList; // Trả về danh sách InformationBrand
    }

    @GetMapping("/getOrder/{id}")
    public List<OrderDetailz> getOrder(@PathVariable("id") Integer id) {
        try {
            List<OrderDetail> orderz = orderDetailRepository.findAllByOrderId(id);
            List<OrderDetailz> orderDetail = new ArrayList<OrderDetailz>();


            for (OrderDetail od : orderz) {
                orderDetail.add(new OrderDetailz(od.getId(), od.getOrder().getState(),
                        od.getProduct().getName(), od.getProduct().getImages(),
                        od.getProduct().getPrice(), od.getQuantity(), StatisticalServiceImpl.dateToLocalDate(od.getCreateDate()), od.getProduct().getDiscounts(), od.getProduct().getId()));
            }
            return orderDetail;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/getOrderNew/{userId}/{productId}")
    public com.onlinemobilestore.dto.ProductInOrderDTO getOrderNew(@PathVariable("userId") int userId,
                                                                   @PathVariable("productId") int productId) {
        return orderService.createOrderAndOrderDetailByUserIdAndByProductId(userId, productId);
    }

    @GetMapping("/getOrders/{userId}")
    public List<OrderForUserDTO> getOrdersForUser(@PathVariable("userId") int userId) {
        return orderService.getOrdersForUser(userId);
    }

    @GetMapping("/deleteOrder/{idOrder}/{idUser}")
    public List<OrderForUserDTO> deleteOrder(@PathVariable Integer idOrder,
                                             @PathVariable Integer idUser) {
        List<OrderForUserDTO> orderList = new ArrayList<>();
        Order orderToDelete = orderRepository.findByIdAndUserId(idOrder, idUser);
        // Kiểm tra xem Order có tồn tại không
        if (orderToDelete != null) {
            // Nếu tồn tại, xóa bằng cách sử dụng hàm delete
            orderRepository.delete(orderToDelete);
            // Tạo danh sách mới sau khi xóa
            return orderService.getOrdersForUser(idUser);
        } else {
            // Nếu không tồn tại, trả về danh sách trống
            return null;
        }
    }

    @GetMapping("/deleteOrderDetail/{idOrderDetail}/{idOrder}")
    public List<OrderDetailz> deleteOrderDetail(
            @PathVariable Integer idOrderDetail,
            @PathVariable Integer idOrder) {

        List<OrderDetailz> orderDetailList = new ArrayList<>();

        // Kiểm tra xem OrderDetail có tồn tại không
        if (orderDetailRepository.existsById(idOrderDetail)) {
            // Nếu tồn tại, xóa bằng cách sử dụng hàm deleteById
            orderDetailRepository.deleteById(idOrderDetail);

            // Tạo danh sách mới sau khi xóa
            for (OrderDetail od : orderDetailRepository.findAllByOrderId(idOrder)) {

                orderDetailList.add(new OrderDetailz(od.getId(), od.getOrder().getState(),
                        od.getProduct().getName(), od.getProduct().getImages(),
                        od.getPrice(), od.getQuantity(), StatisticalServiceImpl.dateToLocalDate(od.getCreateDate()), od.getProduct().getDiscounts(), od.getProduct().getId()));

            }
        } else {
            return orderDetailList;
        }
        return orderDetailList;
    }

    @GetMapping("/api/discount/{name}/{orderId}")
    public ResponseEntity<List<OrderDetail>> getDiscount(@PathVariable("name") String name,
                                                         @PathVariable("orderId") int orderId) {
        Discount discount = discountService.findDiscountByName(name);
        if (discount == null) {
            return ResponseEntity.notFound().build();
        }
        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrderId(orderId);
        orderDetails.forEach(orderDetail -> {
            if (orderDetail.getProduct().getId() == discount.getProduct().getId()) {
                double discountedPrice = orderDetail.getPrice() * (1 - (discount.getPercent() / 100.0));
                orderDetail.setPrice(discountedPrice);
                orderDetailRepository.save(orderDetail);
            }
        });
        return ResponseEntity.ok(orderDetails);
    }

    @GetMapping("/getProductsColor/{nameProduct}/{colorId}")
    public List<Information> getProductsByNameAndColorId(@PathVariable("nameProduct") String nameProduct,
                                                         @PathVariable("colorId") Integer colorId) {
        List<Product> products = productRepository.findByName(nameProduct);
        List<Color> colors = products.stream().map(Product::getColor).collect(Collectors.toList());
        List<Storage> storages = products.stream().map(Product::getStorage).collect(Collectors.toList());
        List<Information> result = new ArrayList<>();

        for (Product product : products) {
            if (product.getColor().getId() == colorId) {
                DataProductDetail productDetail = new DataProductDetail(
                        product.getId(),
                        product.getName(),
                        product.getPrice() * (1 - (product.getPercentDiscount() / 100)),
                        product.getPrice(),
                        product.getDiscounts(),
                        product.getStorage(),
                        product.getColor(),
                        product.getImages(),
                        product.getPreviews().stream().mapToDouble(Preview::getRate).average().orElse(0.0)
                );

                List<DataComment> dataComments = product.getPreviews().stream()
                        .map(preview -> new DataComment(
                                preview.getContent(),
                                preview.getUser().getFullName(),
                                preview.getUser().getAvatar(),
                                preview.getUser().getId(),
                                preview.getRate(),
                                preview.getCreateDate(),
                                preview.getRepPreviews().stream()
                                        .map(repPreview -> new DataRepComment(
                                                repPreview.getId(),
                                                repPreview.getCreateDate(),
                                                repPreview.getContent(),
                                                repPreview.getAdmin().getFullName()
                                        ))
                                        .collect(Collectors.toList())
                        ))
                        .collect(Collectors.toList());


                result.add(new Information(
                        colors,
                        storages,
                        productDetail,
                        dataComments
                ));
            }
        }
        return result;
    }

    @GetMapping("/getProducts/{nameProduct}/{ramId}")
    public List<Information> getProductsByNameAndRamId(@PathVariable("nameProduct") String nameProduct,
                                                       @PathVariable("ramId") Integer ramId) {
        List<Product> products = productRepository.findByName(nameProduct);
        List<Color> colors = products.stream().map(Product::getColor).collect(Collectors.toList());
        List<Storage> storages = products.stream().map(Product::getStorage).collect(Collectors.toList());
        List<Information> result = new ArrayList<>();

        for (Product product : products) {
            if (product.getStorage().getId() == ramId) {
                DataProductDetail productDetail = new DataProductDetail(
                        product.getId(),
                        product.getName(),
                        product.getPrice() * (1 - (product.getPercentDiscount() / 100)),
                        product.getPrice(),
                        product.getDiscounts(),
                        product.getStorage(),
                        product.getColor(),
                        product.getImages(),
                        product.getPreviews().stream().mapToDouble(Preview::getRate).average().orElse(0.0)
                );

                List<DataComment> dataComments = product.getPreviews().stream()
                        .map(preview -> new DataComment(
                                preview.getContent(),
                                preview.getUser().getFullName(),
                                preview.getUser().getAvatar(),
                                preview.getUser().getId(),
                                preview.getRate(),
                                preview.getCreateDate(),
                                preview.getRepPreviews().stream()
                                        .map(repPreview -> new DataRepComment(
                                                repPreview.getId(),
                                                repPreview.getCreateDate(),
                                                repPreview.getContent(),
                                                repPreview.getAdmin().getFullName()
                                        ))
                                        .collect(Collectors.toList())
                        ))
                        .collect(Collectors.toList());


                result.add(new Information(
                        colors,
                        storages,
                        productDetail,
                        dataComments
                ));
            }
        }

        return result;
    }

    @GetMapping("/getProductByRam/{ram}")
    public List<ProductByRam> getInformationProduct(@PathVariable("ram") Integer ram) {
        List<Product> products = productRepository.findByStorage_ReadOnlyMemoryValue(ram);
        List<ProductByRam> result = products.stream().map(data -> {
            List<String> imageList = Optional.ofNullable(data.getImages())
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(Image::getImageUrl)
                    .collect(Collectors.toList());
            List<DiscountProduct> discounts = Optional.ofNullable(data.getDiscounts())
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(discount -> new DiscountProduct(discount.getId(), discount.getName(), discount.getPercent(), discount.getExpirationDate()))
                    .collect(Collectors.toList());
            double priceOld = data.getPrice() * (100 - Optional.ofNullable(data.getPercentDiscount()).orElse(0));
            double rate = Optional.ofNullable(data.getPreviews())
                    .orElse(Collections.emptyList())
                    .stream()
                    .mapToDouble(Preview::getRate)
                    .average()
                    .orElse(0.0);
            ProductDetail productDetail = new ProductDetail(data.getId(), data.getName(), data.getColor().getColor(), data.getPrice(), priceOld, data.getStorage(), rate);
            List<Comment> commentList = Optional.ofNullable(data.getPreviews())
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(preview -> {
                        List<RepComment> repCommentList = Optional.ofNullable(preview.getRepPreviews())
                                .orElse(Collections.emptyList())
                                .stream()
                                .map(repPreview -> new RepComment(
                                        repPreview.getId(),
                                        repPreview.getCreateDate(),
                                        repPreview.getContent(),
                                        repPreview.getAdmin() != null ? repPreview.getAdmin().getFullName() : ""))
                                .collect(Collectors.toList());

                        return new Comment(
                                preview.getId(),
                                preview.getCreateDate(),
                                preview.getRate(),
                                preview.getContent(),
                                preview.getUser() != null ? preview.getUser().getFullName() : "",
                                repCommentList);
                    })
                    .collect(Collectors.toList());

            return new ProductByRam(data.getId(), imageList, discounts, productDetail, commentList);
        }).collect(Collectors.toList());

        return result;
    }

    @GetMapping("/getInformation/{id}")
    public Information tgetInformationProduct(@PathVariable("id") Integer id) {
        Product data = productRepository.findById(id).get();
        List<Product> productList = data.getCategory().getProducts();
        System.out.println(productList.size());
        List<Color> colorList = new ArrayList<>();
        List<Storage> storageList = new ArrayList<>();
        List<DataComment> commentList = new ArrayList<>();
        data.getPreviews().stream().forEach(preview -> {
            List<DataRepComment> repCommentList = new ArrayList<>();
            preview.getRepPreviews().stream().forEach(repPreview -> repCommentList.add(new DataRepComment(repPreview.getId(), repPreview.getCreateDate(), repPreview.getContent(), repPreview.getAdmin().getFullName())));
            commentList.add(new DataComment(preview.getContent(), preview.getUser().getFullName(), preview.getUser().getAvatar(), preview.getUser().getId(), preview.getRate(), preview.getCreateDate(), repCommentList));
        });
        productList.stream().forEach(productDetail -> {
            colorList.add(productDetail.getColor());
            storageList.add(productDetail.getStorage());
        });
        Double rate = commentList.stream().mapToDouble(DataComment::getRate)
                .average().orElse(0);
        DataProductDetail dataProductDetail = new DataProductDetail(data.getId(), data.getName(), data.getPrice() * (100 - data.getPercentDiscount()), data.getPrice(), data.getDiscounts(), data.getStorage(), data.getColor(), data.getImages(), rate);
        return new Information(colorList, storageList, dataProductDetail, commentList);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ProductInOrderDTO {
        private int id;
        private Color color;
        private int productId;
        private Date createDate;
        private String nameProduct;
        private double price;
        private List<String> images;
        private int quantity;
        private boolean state;

    }

    @Data
    @AllArgsConstructor
    class DiscountProduct {
        private Integer id;
        private String name;
        private double percent;
        private Date expirationDate;
    }

    @Data
    @AllArgsConstructor
    class ProductDetail {
        private int id;
        private String name;
        private String color;
        private double priceNew;
        private double priceOld;
        private Storage storage;
        private double rate;
    }

    @Data
    @AllArgsConstructor
    class OrderDetailz {

        private int id;
        private int state;
        private String name;
        private List<Image> images;
        private double price;
        private int quantity;
        private LocalDate createDate;
        private List<Discount> discounts;
        private int idProduct;


    }

    @Data
    @AllArgsConstructor
    class DataProductHasDiscount {
        private Integer id;
        private List<Image> images;
        private List<Storage> storages;
        private String name;
        private Double price;
        private Double priceUpdate;
        private int percentDiscount;
        private List<DiscountBrief> discount;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class DiscountBrief {
        private String name;
        private double percent;
        private Date expiration_date;
        private Date create_date;
        private boolean active;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class ProductByRam {
        private Integer id;
        private List<String> imageList;
        private List<DiscountProduct> discountProducts;
        private ProductDetail productDetail;
        private List<Comment> commentList;
    }

    @Data
    @AllArgsConstructor
    class InformationBrand {
        private int id;
        private String name;
        private double price;
        private double priceUpdate;
        private int quantity;
        private String description;
        private List<Image> images;
        private Date createDate;
        private Date modifiedDate;
        private float percentDiscount;
        private List<Storage> storages;

    }

    @Data
    @AllArgsConstructor
    class DataRepComment {
        private int id;
        private Date createDate;
        private String content;
        private String fullName;
    }

    @Data
    @AllArgsConstructor
    class Comment {
        private int id;
        private Date createDate;
        private double rate;
        private String content;
        private String userName;
        private List<RepComment> repCommentList;
    }

    @Data
    @AllArgsConstructor
    class Information {
        private List<Color> colors;
        private List<Storage> storages;
        private DataProductDetail dataProductDetail;
        private List<DataComment> commentList;
    }

    @Data
    @AllArgsConstructor
    class RepComment {
        private int id;
        private Date createDate;
        private String content;
        private String adminName;
    }

    @Data
    @AllArgsConstructor
    class DataComment {
        private String content;
        private String nameUser;
        private String avatar;
        private Integer idUser;
        private Double rate;
        private Date createDate;
        private List<DataRepComment> repPreviews;
    }
//    End


    @Data
    @AllArgsConstructor
    class DataProductDetail {
        private Integer id;
        private String name;
        private Double priceNew;
        private Double priceOld;
        private List<Discount> discounts;
        private Storage storage;
        private Color color;
        private List<Image> images;
        private Double rate;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class UserData {
        private Integer id;
        private String email;
        private String address;
        private String fullName;
        private String avatar;
        private Date birthday;
        private String phoneNumber;
        private Date createDate;
        private boolean isActive;
        private String roles;
    }

//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public class UsersAndMsgDTO {
//        private String message;
//        private List<UserDTO> userDataList;
//    }
}