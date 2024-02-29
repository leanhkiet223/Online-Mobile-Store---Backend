package com.onlinemobilestore.services.serviceImpl;

import com.onlinemobilestore.dto.ProductRateDto;
import com.onlinemobilestore.dto.ProductStatisticalDto;
import com.onlinemobilestore.entity.*;
import com.onlinemobilestore.repository.*;
import com.onlinemobilestore.services.servicelnterface.StatisticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticalServiceImpl implements StatisticalService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private PreviewRepository previewRepository;

    @Override
    public int totalActiveUsersInCurrentMonth() {
        LocalDate currentDate = LocalDate.now();
        List<User> users = userRepository.findAll();
        int totalActiveUsersInCurrentMonth = users.stream()
                .filter(user -> dateToLocalDate(user.getCreateDate()).getMonth() == currentDate.getMonth()
                        && dateToLocalDate(user.getCreateDate()).getYear() == currentDate.getYear()
                        && user.isActive() == true
                        && user.getRoles().contains("USER")
                ).mapToInt(user -> 1).sum();
        return totalActiveUsersInCurrentMonth;
    }

    @Override
    public int totalActiveUsersOfLastMonth() {
        LocalDate currentDate = LocalDate.now();
        LocalDate firstDayOfMonth = LocalDate.of(currentDate.getYear(),currentDate.minusMonths(1).getMonth(),1);
        List<User> users = userRepository.findAll();
        int totalActiveUsersOfLastMonth = users.stream()
                .filter(user -> dateToLocalDate(user.getCreateDate()).isAfter(firstDayOfMonth)
                        && dateToLocalDate(user.getCreateDate()).isBefore(currentDate.withDayOfMonth(1))
                        && user.isActive() == true
                        && user.getRoles().contains("USER")
                ).mapToInt(user -> 1).sum();
        return totalActiveUsersOfLastMonth;
    }

    @Override
    public double userComparison(int totalActiveUsersInCurrentMonth, int totalActiveUsersOfLastMonth) {
        if(totalActiveUsersOfLastMonth == 0){
            return 100.0;
        }
        return ((totalActiveUsersInCurrentMonth-totalActiveUsersOfLastMonth)/totalActiveUsersOfLastMonth *100);
    }

    @Override
    public int totalActiveUsers() {
        return total(true,"USER");
    }

    @Override
    public int totalInactiveUsers() {
        return total(false,"USER");
    }

    @Override
    public int totalActiveAdmin() {
        return total(true,"ADMIN");
    }

    @Override
    public int totalNumberOfUsersPurchasingTheProduct() {
        List<User> users = userRepository.findAll();
        int total = (int) users.stream()
                .filter(user -> user.isActive() &&
                        user.getRoles().contains("USER") &&
                        user.getOrders() != null && !user.getOrders().isEmpty())
                .count();
        return total;
    }


    @Override
    public double revenueOfCurrentMonth() {
        LocalDate currentDate  = LocalDate.now();
        List<Order> orders = orderRepository.findAll();
        double revenueOfCurrentMonth = orders.stream()
                .filter(order ->
                        dateToLocalDate(order.getCreateDate()).getMonth() == currentDate.getMonth() &&
                                dateToLocalDate(order.getCreateDate()).getYear() == currentDate.getYear())
                .mapToDouble(Order::getTotal)
                .sum();
        return revenueOfCurrentMonth;
    }

    @Override
    public double revenueOfLastMonth() {
        LocalDate currentDate = LocalDate.now();
        List<Order> orders = orderRepository.findAll();
        LocalDate firstDayOfLastMonth = LocalDate.of(currentDate.getYear(), currentDate.minusMonths(1).getMonth(), 1);
        double  revenueOfLastMonth = orders.stream()
                .filter(order ->
                        dateToLocalDate(order.getCreateDate()).isAfter(firstDayOfLastMonth.minusDays(1)) &&
                                dateToLocalDate(order.getCreateDate()).isBefore(currentDate.withDayOfMonth(1)))
                .mapToDouble(Order::getTotal)
                .sum();
        return revenueOfLastMonth;
    }

    @Override
    public double revenueComparison(double revenueOfCurrentMonth, double revenueOfLastMonth) {
        if (revenueOfLastMonth == 0) {
            return 100;
        }
        return ((revenueOfCurrentMonth - revenueOfLastMonth) / revenueOfLastMonth) * 100;
    }

    @Override
    public int totalProductsInCurrentMonth() {
        LocalDate currentDate = LocalDate.now();
        List<Product> products = productRepository.findAll();
        int totalProductsInCurrentMonth = products.stream()
                .filter(product -> dateToLocalDate(product.getCreateDate()).getMonth() == currentDate.getMonth() &&
                        dateToLocalDate(product.getCreateDate()).getYear() == currentDate.getYear())
                .mapToInt(Product::getQuantity)
                .sum();
        return totalProductsInCurrentMonth;
    }

    @Override
    public int totalQuantitySoldInCurrentMonth() {
        LocalDate currentDate = LocalDate.now();
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        int totalQuantitySoldInCurrentMonth = orderDetails.stream()
                .filter(orderDetail -> dateToLocalDate(orderDetail.getCreateDate()).getMonth() == currentDate.getMonth() &&
                        dateToLocalDate(orderDetail.getCreateDate()).getYear() == currentDate.getYear())
                .mapToInt(OrderDetail::getQuantity)
                .sum();
        return totalQuantitySoldInCurrentMonth;
    }

    @Override
    public int totalStockQuantityInCurrentMonth(int totalProductsInCurrentMonth, int totalQuantitySoldInCurrentMonth) {
        return totalProductsInCurrentMonth - totalQuantitySoldInCurrentMonth;
    }

    @Override
    public int totalProductsOfLastMonth() {
        LocalDate currentDate = LocalDate.now();
        LocalDate firstDayOfLastMonth = LocalDate.of(currentDate.getYear(), currentDate.minusMonths(1).getMonth(),1);
        List<Product> products = productRepository.findAll();
        int totalProductsOfLastMonth = products.stream()
                .filter(product -> dateToLocalDate(product.getCreateDate()).isAfter(firstDayOfLastMonth) &&
                        dateToLocalDate(product.getCreateDate()).isBefore(currentDate.withDayOfMonth(1)))
                .mapToInt(Product::getQuantity)
                .sum();
        return totalProductsOfLastMonth;
    }

    @Override
    public double productComparison(int totalProductsInCurrentMonth, int totalProductsOfLastMonth) {
        if(totalProductsOfLastMonth == 0){
            return 100.0;
        }
        return ((totalProductsInCurrentMonth - totalProductsOfLastMonth)/totalProductsOfLastMonth * 100);
    }

    @Override
    public int totalProducts() {
        List<Product> products = productRepository.findAll();
        int totalProducts = products.stream().mapToInt(Product::getQuantity).sum();
        return totalProducts;
    }

    @Override
    public int totalQuantitySold() {
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        int totalQuantitySold = orderDetails.stream().mapToInt(OrderDetail::getQuantity).sum();
        return totalQuantitySold;
    }

    @Override
    public int totalStockQuantity(int totalProducts, int totalQuantitySold) {
        return totalProducts - totalQuantitySold;
    }

    @Override
    public List<ProductStatisticalDto> bestSellingProductsInCurrentMonth() {
        LocalDate currentDate = LocalDate.now();
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        Map<Product, Integer> productQuantityMap = orderDetails.stream()
                .filter(orderDetail ->
                        dateToLocalDate(orderDetail.getCreateDate()).getMonth() == currentDate.getMonth() &&
                                dateToLocalDate(orderDetail.getCreateDate()).getYear() == currentDate.getYear())
                .collect(Collectors.groupingBy(OrderDetail::getProduct,
                        Collectors.summingInt(OrderDetail::getQuantity)));
        int maxQuantity = productQuantityMap.values().stream()
                .max(Integer::compare)
                .orElse(0);
        List<ProductStatisticalDto> bestSellingProductsDTO = productQuantityMap.entrySet().stream()
                .filter(entry -> entry.getValue() == maxQuantity)
                .map(entry -> new ProductStatisticalDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return bestSellingProductsDTO;
    }

    @Override
    public List<ProductStatisticalDto> bestSellingProducts() {
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();

        Map<Product, Integer> productQuantityMap = orderDetails.stream()
                .collect(Collectors.groupingBy(OrderDetail::getProduct,
                        Collectors.summingInt(OrderDetail::getQuantity)));
        int maxQuantity = productQuantityMap.values().stream().max(Integer::compare).orElse(0);

        List<ProductStatisticalDto> bestSellingProductsDTO = productQuantityMap.entrySet().stream()
//                .sorted(Map.Entry.<Product, Integer>comparingByValue().reversed())
                .filter(entry -> entry.getValue() == maxQuantity)
                .map(entry -> new ProductStatisticalDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return bestSellingProductsDTO;
    }

    @Override
    public OptionalDouble averageRating() {
        List<Preview> previews = previewRepository.findAll();
        return previews.stream().mapToDouble(Preview::getRate).average();
    }

    @Override
    public OptionalDouble averageRatingInCurrentMonth() {
        LocalDate currentDate =  LocalDate.now();
        List<Preview> previews = previewRepository.findAll();
        OptionalDouble averageRatingInCurrentMonth = previews.stream()
                .filter(preview -> dateToLocalDate(preview.getCreateDate()).getMonth() == currentDate.getMonth() && dateToLocalDate(preview.getCreateDate()).getYear() == currentDate.getYear() )
                .mapToDouble(Preview::getRate)
                .average();
        return averageRatingInCurrentMonth;
    }

    @Override
    public OptionalDouble averageRatingOfLastMonth() {
        LocalDate currentDate = LocalDate.now();
        LocalDate firstDayOfLastMonth = LocalDate.of(currentDate.getYear(), currentDate.minusMonths(1).getMonth(),1);
        List<Preview> previews = previewRepository.findAll();
        OptionalDouble averageRatingOfLastMonth = previews.stream()
                .filter(preview -> dateToLocalDate(preview.getCreateDate()).isAfter(firstDayOfLastMonth) &&
                        dateToLocalDate(preview.getCreateDate()).isBefore(currentDate.withDayOfMonth(1)))
                .mapToDouble(Preview::getRate)
                .average();
        return averageRatingOfLastMonth;
    }

    @Override
    public double averageRatingComparison(OptionalDouble averageRatingInCurrentMonth, OptionalDouble averageRatingOfLastMonth) {
        double currentMonthRevenueValue = averageRatingInCurrentMonth.orElse(0.0);
        double lastMonthRevenueValue = averageRatingOfLastMonth.orElse(0.0);
        if(lastMonthRevenueValue == 0){
            return 100.0;
        }
        return ((currentMonthRevenueValue - lastMonthRevenueValue)/lastMonthRevenueValue *100 );
    }

    @Override
    public List<ProductRateDto> highestRatedProduct() {
        List<Preview> previews = previewRepository.findAll();

        Map<Product, Double> averageRate = previews.stream()
                .collect(Collectors.groupingBy(Preview::getProduct,
                        Collectors.averagingDouble(Preview::getRate)));
        if (averageRate.isEmpty()) {
            return Collections.emptyList();
        }
        double epsilon = 1e-6;

        double highestRatedProduct = averageRate.values().stream()
                .max(Double::compare)
                .orElse(0.0);
        List<ProductRateDto> highestRatedProducts = averageRate.entrySet().stream()
                .filter(entry -> Math.abs(entry.getValue() - highestRatedProduct) < epsilon)
                .map(entry -> new ProductRateDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return highestRatedProducts;
    }


    @Override
    public List<ProductRateDto> lowestRatedProduct() {
        List<Preview> previews = previewRepository.findAll();
        Map<Product, Double> averageRate = previews.stream()
                .collect(
                        Collectors.groupingBy(Preview::getProduct,
                                Collectors.averagingDouble(Preview::getRate)));
        if(averageRate.isEmpty()){
            return Collections.emptyList();
        }
        double epsilon = 1e-6;
        double lowestRatedProduct = averageRate.values().stream().min(Double::compare).orElse(0.0);
        List<ProductRateDto> lowestRatedProducts = averageRate.entrySet().stream()
                .filter(entry -> Math.abs(entry.getValue() - lowestRatedProduct) < epsilon)
                .map(entry -> new ProductRateDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return lowestRatedProducts;
    }

    @Override
    public double[] revenueByMonth() {
        LocalDate currentDate = LocalDate.now();
        List<Order> orders = orderRepository.findAll();
        LocalDate firstDayOfYear = LocalDate.of(currentDate.getYear(), 1, 1);

        List<Order> ordersCurrentYear = orders.stream()
                .filter(order ->
                        dateToLocalDate(order.getCreateDate()).isAfter(firstDayOfYear.minusDays(1)) &&
                                dateToLocalDate(order.getCreateDate()).isBefore(currentDate.plusDays(1)))
                .collect(Collectors.toList());

        double[] revenueByMonth = new double[12];
        for (Order order : ordersCurrentYear) {
            int monthValue = dateToLocalDate(order.getCreateDate()).getMonthValue();
            revenueByMonth[monthValue - 1] += order.getTotal();
        }

        return revenueByMonth;
    }




    @Override
    public int[] totalOrdersByMonth() {
        LocalDate currentDate = LocalDate.now();
        LocalDate firstDayOfYear = LocalDate.of(currentDate.getYear(), 1, 1);
        List<Order> orders = orderRepository.findAll();
        List<Order> ordersCurrentYear = orders.stream()
                .filter(order ->
                        dateToLocalDate(order.getCreateDate()).isAfter(firstDayOfYear.minusDays(1)) &&
                                dateToLocalDate(order.getCreateDate()).isBefore(currentDate.plusDays(1)))
                .collect(Collectors.toList());
        int[] totalOrdersByMonth = new int[12];
        for (Order order : ordersCurrentYear) {
            int monthValue = dateToLocalDate(order.getCreateDate()).getMonthValue();
            totalOrdersByMonth[monthValue - 1] += 1;
        }
        return totalOrdersByMonth;
    }

    public int total(Boolean isActive, String role){
        List<User> users = userRepository.findAll();

        int total = users.stream().filter(user -> user.isActive() == isActive && user.getRoles().contains(role))
                .mapToInt(user-> 1).sum();
        return total;

    }
    public static LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atOffset(ZoneOffset.UTC).toLocalDate();
    }
}


