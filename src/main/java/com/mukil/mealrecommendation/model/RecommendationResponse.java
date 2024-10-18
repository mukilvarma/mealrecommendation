package com.mukil.mealrecommendation.model;

import com.fasterxml.jackson.databind.JsonNode;

public class RecommendationResponse {
    private JsonNode recipes;
    private JsonNode restaurants;
    private String message;

    public JsonNode getRecipes() {
        return recipes;
    }

    public void setRecipes(JsonNode recipes) {
        this.recipes = recipes;
    }

    public JsonNode getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(JsonNode restaurants) {
        this.restaurants = restaurants;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
