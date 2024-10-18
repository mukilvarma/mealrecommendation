package com.mukil.mealrecommendation.service;

import com.mukil.mealrecommendation.interfaces.NutritionAnalysisService;
import com.mukil.mealrecommendation.interfaces.RestaurantSearchService;
import com.mukil.mealrecommendation.model.MenuItem;
import com.mukil.mealrecommendation.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HealthyRecipeService {

    private final RestaurantSearchService restaurantSearchService;
    private final NutritionAnalysisService nutritionAnalysisService;

    @Autowired
    public HealthyRecipeService(RestaurantSearchService restaurantSearchService,
                                NutritionAnalysisService nutritionAnalysisService) {
        this.restaurantSearchService = restaurantSearchService;
        this.nutritionAnalysisService = nutritionAnalysisService;
    }

    public List<MenuItem> getTopHealthyItems(String cuisineType, String city) throws Exception {
        List<Restaurant> restaurants = restaurantSearchService.searchRestaurantsByCuisineAndCity(cuisineType, city);

        List<MenuItem> allHealthyItems = new ArrayList<>();

        for (Restaurant restaurant : restaurants) {
            List<MenuItem> menuItems = restaurantSearchService.getMenuItemsForRestaurant(restaurant.getPlaceId());

            // Analyze each menu item and add to healthy items list
            for (MenuItem item : menuItems) {
                MenuItem analyzedItem = nutritionAnalysisService.analyzeNutrition(item);
                if (analyzedItem != null && analyzedItem.getCalories() <= 400) {
                    allHealthyItems.add(analyzedItem);
                }
            }
        }

        // Sort and limit to top 10
        return allHealthyItems.stream()
                .sorted(Comparator.comparingInt(MenuItem::getCalories))
                .limit(10)
                .collect(Collectors.toList());
    }
}
