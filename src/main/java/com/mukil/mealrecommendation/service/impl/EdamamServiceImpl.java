package com.mukil.mealrecommendation.service.impl;

import com.mukil.mealrecommendation.interfaces.NutritionAnalysisService;
import com.mukil.mealrecommendation.model.RecipeNutrition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class EdamamServiceImpl implements NutritionAnalysisService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl = "https://api.edamam.com/api/nutrition-data";
    private final String appId = "your-app-id";    // Replace with your actual App ID
    private final String appKey = "your-app-key";  // Replace with your actual App Key

    @Override
    public RecipeNutrition analyzeNutrition(String recipe) {
        Map<String, Object> nutritionData;

        // Prepare the API request URL
        String requestUrl = apiUrl + "?app_id=" + appId + "&app_key=" + appKey + "&ingr=" + recipe;

        try {
            // Attempt to get data from the Edamam API
            nutritionData = restTemplate.getForObject(requestUrl, HashMap.class);

            // Check if response is empty or invalid
            if (nutritionData == null || nutritionData.isEmpty()) {
                nutritionData = getDummyNutritionData();
            }

        } catch (RestClientException e) {
            // If an error occurs, return dummy data
            System.out.println("Error fetching nutrition data from Edamam API: " + e.getMessage());
            nutritionData = getDummyNutritionData();
        }

        return mapToRecipeNutrition(nutritionData, recipe);
    }

    private RecipeNutrition mapToRecipeNutrition(Map<String, Object> nutritionData, String recipe) {
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

    // Dummy nutrition data fallback
    private Map<String, Object> getDummyNutritionData() {
        Map<String, Object> dummyData = new HashMap<>();

        // Populating with dummy nutritional values
        dummyData.put("calories", 250);
        dummyData.put("totalWeight", 100);
        dummyData.put("dietLabels", new String[] { "Low-Carb", "High-Protein" });
        dummyData.put("healthLabels", new String[] { "Gluten-Free", "Peanut-Free", "Soy-Free" });
        dummyData.put("totalNutrients", Map.of(
                "FAT", Map.of("label", "Fat", "quantity", 12.0, "unit", "g"),
                "CHOCDF", Map.of("label", "Carbs", "quantity", 30.0, "unit", "g"),
                "PROCNT", Map.of("label", "Protein", "quantity", 20.0, "unit", "g"),
                "SUGAR", Map.of("label", "Sugar", "quantity", 15.0, "unit", "g")
        ));

        dummyData.put("totalDaily", Map.of(
                "FAT", Map.of("label", "Fat", "quantity", 18.0, "unit", "%"),
                "CHOCDF", Map.of("label", "Carbs", "quantity", 10.0, "unit", "%"),
                "PROCNT", Map.of("label", "Protein", "quantity", 40.0, "unit", "%")
        ));

        return dummyData;
    }
}
