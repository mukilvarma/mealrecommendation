package com.mukil.mealrecommendation.service.impl;

import com.mukil.mealrecommendation.interfaces.RestaurantSearchService;
import com.mukil.mealrecommendation.model.MenuItem;
import com.mukil.mealrecommendation.model.Restaurant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaticRestaurantServiceImpl implements RestaurantSearchService {

    @Override
    public List<Restaurant> searchRestaurantsByCuisineAndCity(String cuisineType, String city) {
        return getMockRestaurants(cuisineType, city);
    }

    @Override
    public List<MenuItem> getMenuItemsForRestaurant(String placeId) {
        return List.of(
                new MenuItem("Grilled Chicken Salad", "A healthy salad with grilled chicken and mixed greens.", 12.99),
                new MenuItem("Veggie Pizza", "A delicious pizza topped with fresh vegetables and cheese.", 14.99),
                new MenuItem("Pasta Primavera", "Pasta with a mix of seasonal vegetables in a light sauce.", 13.49),
                new MenuItem("Beef Tacos", "Tacos filled with seasoned beef and fresh toppings.", 11.99),
                new MenuItem("Chocolate Cake", "Rich chocolate cake topped with creamy frosting.", 6.99)
        );
    }

    private List<Restaurant> getMockRestaurants(String cuisineType, String city) {
        List<Restaurant> mockRestaurants = new ArrayList<>();

        // Example mock data with placeId
        mockRestaurants.add(new Restaurant("The Spicy Kitchen", "123 Flavor St, " + city, "Chinese", "place1"));
        mockRestaurants.add(new Restaurant("Taste of Italy", "456 Pasta Rd, " + city, "Italian", "place2"));
        mockRestaurants.add(new Restaurant("Sushi Paradise", "789 Sushi Ln, " + city, "Japanese", "place3"));
        mockRestaurants.add(new Restaurant("Curry House", "101 Spice Blvd, " + city, "Indian", "place4"));
        mockRestaurants.add(new Restaurant("Burger Joint", "234 Burger St, " + city, "American", "place5"));

        return mockRestaurants;
    }
}
