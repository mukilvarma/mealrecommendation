package com.mukil.mealrecommendation.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mukil.mealrecommendation.interfaces.NutritionAnalysisService;
import com.mukil.mealrecommendation.model.MenuItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLEncoder;

@Service
public class EdamamServiceImpl implements NutritionAnalysisService {

    @Value("${api.edamam.app-id}")
    private String edamamAppId;

    @Value("${api.edamam.app-key}")
    private String edamamAppKey;

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public MenuItem analyzeNutrition(MenuItem item) throws Exception {
        String query = item.getName() + " " + item.getDescription();
        String edamamUrl = String.format(
                "https://api.edamam.com/api/nutrition-data?app_id=%s&app_key=%s&ingr=%s",
                edamamAppId, edamamAppKey, URLEncoder.encode(query, "UTF-8")
        );

        String response = restTemplate.getForObject(edamamUrl, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response);

        double calories = root.path("calories").asDouble();
        double fat = root.path("totalNutrients").path("FAT").path("quantity").asDouble();
        double carbs = root.path("totalNutrients").path("CHOCDF").path("quantity").asDouble();
        double protein = root.path("totalNutrients").path("PROCNT").path("quantity").asDouble();

        item.setCalories((int) calories);
        item.setFat(fat);
        item.setCarbohydrates(carbs);
        item.setProtein(protein);

        return item;
    }
}
