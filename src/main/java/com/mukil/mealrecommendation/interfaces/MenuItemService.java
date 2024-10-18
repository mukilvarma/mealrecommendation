package com.mukil.mealrecommendation.interfaces;

import com.fasterxml.jackson.databind.JsonNode;
import com.mukil.mealrecommendation.model.MenuItem;

import java.util.List;

public interface MenuItemService {
    List<MenuItem> extractMenuItems(JsonNode restaurantDetails);
}
