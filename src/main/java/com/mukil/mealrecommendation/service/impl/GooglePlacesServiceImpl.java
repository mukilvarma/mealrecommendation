package com.mukil.mealrecommendation.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mukil.mealrecommendation.exception.ResourceNotFoundException;
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
            throw new ResourceNotFoundException("We couldn't find restaurants by this time");
        }
        return restaurants;
    }

    @Override
    public List<MenuItem> getMenuItemsForRestaurant(String placeId) throws Exception {
        throw new Exception("Menu items API not implemented");
    }
}
