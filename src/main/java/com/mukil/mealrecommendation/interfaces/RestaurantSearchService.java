package com.mukil.mealrecommendation.interfaces;

import com.mukil.mealrecommendation.model.MenuItem;
import com.mukil.mealrecommendation.model.Restaurant;

import java.util.List;

public interface RestaurantSearchService {
    List<Restaurant> searchRestaurantsByCuisineAndCity(String cuisineType, String city) throws Exception;
    List<MenuItem> getMenuItemsForRestaurant(String placeId) throws Exception;
}
