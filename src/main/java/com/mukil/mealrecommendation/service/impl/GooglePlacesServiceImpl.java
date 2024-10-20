package com.mukil.mealrecommendation.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mukil.mealrecommendation.interfaces.RestaurantSearchService;
import com.mukil.mealrecommendation.model.MenuItem;
import com.mukil.mealrecommendation.model.Restaurant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GooglePlacesServiceImpl implements RestaurantSearchService {

    @Value("${api.google.api-key}")
    private String googleApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Restaurant> searchRestaurantsByCuisineAndCity(String cuisineType, String city) throws Exception {
        String googlePlacesUrl = String.format(
                "https://maps.googleapis.com/maps/api/place/textsearch/json?query=%s+restaurants+in+%s&key=%s",
                cuisineType, city, googleApiKey
        );
        String response = restTemplate.getForObject(googlePlacesUrl, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response);
        JsonNode results = root.path("results");

        List<Restaurant> restaurants = new ArrayList<>();
        for (JsonNode node : results) {
            Restaurant restaurant = new Restaurant();
            restaurant.setName(node.path("name").asText());
            restaurant.setAddress(node.path("formatted_address").asText());
            restaurant.setPlaceId(node.path("place_id").asText());
            restaurants.add(restaurant);
        }

        if (restaurants.isEmpty()) {
            restaurants = getMockRestaurants(cuisineType, city);
        }
        return restaurants;
    }

    @Override
    public List<MenuItem> getMenuItemsForRestaurant(String placeId) {
        // This should ideally call a real menu API from Zomato or some other APIs - paid
        return extractMenuItems();
    }

    private List<MenuItem> extractMenuItems() {
        // Mock data for demonstration
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
