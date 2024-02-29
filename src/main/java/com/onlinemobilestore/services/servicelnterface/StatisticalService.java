package com.onlinemobilestore.services.servicelnterface;

import com.onlinemobilestore.dto.ProductRateDto;
import com.onlinemobilestore.dto.ProductStatisticalDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.OptionalDouble;

@Component
public interface StatisticalService {
    //-------------------------- USER STATISTICAL
    int totalActiveUsersInCurrentMonth();
    int totalActiveUsersOfLastMonth();
    double userComparison(int totalActiveUsersInCurrentMonth, int totalActiveUsersOfLastMonth);
    int totalActiveUsers();
    int totalInactiveUsers();
    int totalActiveAdmin();
    int totalNumberOfUsersPurchasingTheProduct();
    //-------------------------- ORDER STATISTICAL
    double revenueOfCurrentMonth();
    double revenueOfLastMonth();
    double revenueComparison(double revenueOfCurrentMonth, double revenueOfLastMonth);
    //-------------------------- PRODUCT STATISTICAL
    int totalProductsInCurrentMonth();
    int totalQuantitySoldInCurrentMonth();
    int totalStockQuantityInCurrentMonth(int totalProductsInCurrentMonth, int totalQuantitySoldInCurrentMonth);
    int totalProductsOfLastMonth();
    double productComparison(int totalProductsInCurrentMonth, int totalProductsOfLastMonth);
    int totalProducts();
    int totalQuantitySold();
    int totalStockQuantity(int totalProducts, int totalQuantitySold);
    List<ProductStatisticalDto> bestSellingProductsInCurrentMonth();
    List<ProductStatisticalDto> bestSellingProducts();
    //-------------------------- PREVIEW STATISTICAL
    OptionalDouble averageRating();
    OptionalDouble averageRatingInCurrentMonth();
    OptionalDouble averageRatingOfLastMonth();
    double averageRatingComparison(OptionalDouble averageRatingInCurrentMonth, OptionalDouble averageRatingOfLastMonth);
    List<ProductRateDto> highestRatedProduct();
    List<ProductRateDto> lowestRatedProduct();
    double[]  revenueByMonth();
    int[] totalOrdersByMonth();
}
