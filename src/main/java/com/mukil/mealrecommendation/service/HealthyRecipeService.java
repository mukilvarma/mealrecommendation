package com.mukil.mealrecommendation.service;

import com.mukil.mealrecommendation.interfaces.NutritionAnalysisService;
import com.mukil.mealrecommendation.interfaces.RestaurantSearchService;
import com.mukil.mealrecommendation.model.MenuItem;
import com.mukil.mealrecommendation.model.RecipeNutrition;
import com.mukil.mealrecommendation.model.Restaurant;
import com.mukil.mealrecommendation.factory.ServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HealthyRecipeService {

    private final boolean useMockData;

    private final ServiceFactory serviceFactory;

    @Autowired
    public HealthyRecipeService(ServiceFactory serviceFactory, @Value("${useMockData}") boolean useMockData) {
        this.serviceFactory = serviceFactory;
        this.useMockData = useMockData;
    }

    public List<RecipeNutrition> getTopHealthyItems(String cuisineType, String city) throws Exception {
        // Get the appropriate RestaurantSearchService implementation (real or dummy) from the factory
        RestaurantSearchService restaurantSearchService = serviceFactory.getRestaurantService(useMockData);

        // Get the appropriate NutritionAnalysisService implementation (real or dummy) from the factory
        NutritionAnalysisService nutritionAnalysisService = serviceFactory.getNutritionService(useMockData);

        // Fetch restaurants based on cuisine type and city
        List<Restaurant> restaurants = restaurantSearchService.searchRestaurantsByCuisineAndCity(cuisineType, city);

        List<RecipeNutrition> allHealthyItems = new ArrayList<>();

        // Loop through each restaurant and get menu items
        for (Restaurant restaurant : restaurants) {
            List<MenuItem> menuItems = restaurantSearchService.getMenuItemsForRestaurant(restaurant.getPlaceId());

            // Analyze each menu item for its nutritional content
            for (MenuItem item : menuItems) {
                RecipeNutrition recipeNutrition = nutritionAnalysisService.analyzeNutrition(item.getName());

                // Add only items with 400 calories or fewer to the healthy items list
                if (recipeNutrition != null && recipeNutrition.getCalories() <= 400) {
                    allHealthyItems.add(recipeNutrition);
                }
            }
        }

        // Sort the healthy items by calories and return the top 10
        return allHealthyItems.stream()
                .sorted(Comparator.comparingInt(RecipeNutrition::getCalories))
                .limit(10)
                .collect(Collectors.toList());
    }
}
