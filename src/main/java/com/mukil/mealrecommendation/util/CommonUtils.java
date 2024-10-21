package com.mukil.mealrecommendation.util;

import com.mukil.mealrecommendation.model.RecipeNutrition;

import java.util.Map;

public class CommonUtils {
    public static RecipeNutrition mapToRecipeNutrition(Map<String, Object> nutritionData, String recipe) throws Exception {
        String name = recipe != null ? recipe : ""; // The recipe name as input
        String description = "Description of " + recipe; // Modify this as needed

        // Calories and nutrients are fetched from the API response without default values
        int calories = nutritionData.containsKey("calories") ? (Integer) nutritionData.get("calories") : 0;
        Map<String, Object> totalNutrients = (Map<String, Object>) nutritionData.get("totalNutrients");

        double protein = totalNutrients != null && totalNutrients.containsKey("PROCNT")
                ? (Double) ((Map<String, Object>) totalNutrients.get("PROCNT")).get("quantity")
                : 0;

        double fat = totalNutrients != null && totalNutrients.containsKey("FAT")
                ? (Double) ((Map<String, Object>) totalNutrients.get("FAT")).get("quantity")
                : 0;

        double carbohydrates = totalNutrients != null && totalNutrients.containsKey("CHOCDF")
                ? (Double) ((Map<String, Object>) totalNutrients.get("CHOCDF")).get("quantity")
                : 0;

        return new RecipeNutrition(name, description, calories, protein, fat, carbohydrates);
    }
}
