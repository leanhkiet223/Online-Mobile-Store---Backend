package com.onlinemobilestore.API;

import com.onlinemobilestore.dto.ProductRateDto;
import com.onlinemobilestore.dto.ProductStatisticalDto;
import com.onlinemobilestore.services.serviceImpl.StatisticalServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.OptionalDouble;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminStatisticalAPI {
    @Autowired
    private StatisticalServiceImpl statisticalService;

    @GetMapping(value = "/statistical")
    public ResponseEntity<Statistical> getStatistical(){
        //USER
        int totalActiveUsers = statisticalService.totalActiveUsers();
        int totalInactiveUsers = statisticalService.totalInactiveUsers();
        int totalActiveAdmin = statisticalService.totalActiveAdmin();
        int totalActiveUsersInCurrentMonth = statisticalService.totalActiveUsersInCurrentMonth();
        int totalActiveUsersOfLastMonth = statisticalService.totalActiveUsersOfLastMonth();
        double userComparison = statisticalService.userComparison(totalActiveUsersInCurrentMonth,totalActiveUsersOfLastMonth);
        int totalNumberOfUsersPurchasingTheProduct = statisticalService.totalNumberOfUsersPurchasingTheProduct();
        StatisticalUser statisticalUser = new StatisticalUser(totalActiveUsers,
                totalInactiveUsers,
                totalActiveAdmin,
                totalNumberOfUsersPurchasingTheProduct,
                totalActiveUsersInCurrentMonth,
                totalActiveUsersOfLastMonth,
                userComparison);
        //ORDER
        double revenueOfCurrentMonth = statisticalService.revenueOfCurrentMonth();
        double revenueOfLastMonth = statisticalService.revenueOfLastMonth();
        double revenueComparison = statisticalService.revenueComparison(revenueOfCurrentMonth, revenueOfLastMonth);

        StatisticalOrder statisticalOrder = new StatisticalOrder(revenueOfCurrentMonth,revenueComparison);
        //PRODUCT
        int totalProductsInCurrentMonth = statisticalService.totalProductsInCurrentMonth();
        int totalQuantitySoldInCurrentMonth = statisticalService.totalQuantitySoldInCurrentMonth();
        int totalStockQuantityInCurrentMonth = statisticalService.totalStockQuantityInCurrentMonth(totalProductsInCurrentMonth, totalQuantitySoldInCurrentMonth);
        int totalProductsOfLastMonth = statisticalService.totalProductsOfLastMonth();
        double productComparison = statisticalService.productComparison(totalProductsInCurrentMonth, totalProductsOfLastMonth);

        int totalProducts = statisticalService.totalProducts();
        int totalQuantitySold = statisticalService.totalQuantitySold();
        int totalStockQuantity = statisticalService.totalStockQuantity(totalProducts, totalQuantitySold);
        List<ProductStatisticalDto> bestSellingProductsInCurrentMonth = statisticalService.bestSellingProductsInCurrentMonth();
        List<ProductStatisticalDto> bestSellingProducts= statisticalService.bestSellingProducts();
        StatisticalProduct statisticalProduct = new StatisticalProduct(
                totalProductsInCurrentMonth,
                totalQuantitySoldInCurrentMonth,
                totalStockQuantityInCurrentMonth,
                totalProductsOfLastMonth,
                productComparison,
                totalProducts,
                totalQuantitySold,
                totalStockQuantity,
                bestSellingProductsInCurrentMonth,
                bestSellingProducts);
        //REVIEW
        OptionalDouble averageRating = statisticalService.averageRating();
        OptionalDouble averageRatingInCurrentMonth = statisticalService.averageRatingInCurrentMonth();
        OptionalDouble averageRatingOfLastMonth = statisticalService.averageRatingOfLastMonth();
        double averageRatingComparison = statisticalService.averageRatingComparison(averageRatingInCurrentMonth, averageRatingOfLastMonth);
        List<ProductRateDto> highestRatedProduct = statisticalService.highestRatedProduct();
        List<ProductRateDto> lowestRatedProduct = statisticalService.lowestRatedProduct();
        StatisticalPreview statisticalPreview = new StatisticalPreview(averageRating, averageRatingInCurrentMonth,averageRatingOfLastMonth,averageRatingComparison  , highestRatedProduct, lowestRatedProduct);

        Statistical statistical = new Statistical(statisticalUser, statisticalOrder, statisticalProduct, statisticalPreview );
        return ResponseEntity.ok(statistical);
    }
    @GetMapping(value = "/statisticalOrderAndRevenueByMonth")
    public ResponseEntity<OrderandRevenueByMonth> getStatisticalOrderAndRevenueByMonth(){
        double[]  revenueByMonth = statisticalService.revenueByMonth();
        int[]  totalOrdersByMonth = statisticalService.totalOrdersByMonth();
        OrderandRevenueByMonth orderandRevenueByMonth = new OrderandRevenueByMonth(revenueByMonth, totalOrdersByMonth);
        return ResponseEntity.ok(orderandRevenueByMonth);
    }
    @Data
    @AllArgsConstructor
    class OrderandRevenueByMonth{
        private double[]  revenueByMonth;
        private int[]  totalOrdersByMonth;
    }
    @Data
    @AllArgsConstructor
    class  StatisticalProduct{
        private int totalProductsInCurrentMonth;
        private int totalQuantitySoldInCurrentMonth;
        private int totalStockQuantityInCurrentMonth;
        private int totalProductsOfLastMonth;
        private double productComparison;

        private int totalProducts;
        private int totalQuantitySold;
        private int totalStockQuantity;

        private List<ProductStatisticalDto> bestSellingProductsInCurrentMonth;
        private List<ProductStatisticalDto> bestSellingProducts;

    }
    @Data
    @AllArgsConstructor
    class StatisticalPreview{
        private OptionalDouble averageRating;
        private OptionalDouble averageRatingInCurrentMonth;
        private OptionalDouble averageRatingOfLastMonth;
        private double averageRatingComparison;
        private List<ProductRateDto> highestRatedProduct;
        private List<ProductRateDto> lowestRatedProduct;

    }

    @Data
    @AllArgsConstructor
    class StatisticalOrder{
        private double revenueOfCurrentMonth;
        private double revenueComparison;

    }

    @Data
    @AllArgsConstructor
    class StatisticalUser{
        private int totalActiveUsers;
        private int totalInactiveUsers;
        private int totalActiveAdmin;
        private int totalNumberOfUsersPurchasingTheProduct;
        private int totalActiveUsersInCurrentMonth;
        private int totalActiveUsersOfLastMonth;
        private double userComparison;
    }

    @Data
    @AllArgsConstructor
    class  Statistical{
        private  StatisticalUser statisticalUser;
        private  StatisticalOrder statisticalOrder;
        private  StatisticalProduct statisticalProduct;
        private  StatisticalPreview  statisticalPreview;
    }

}
