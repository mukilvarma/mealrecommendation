package com.mukil.mealrecommendation.service.impl;

import com.mukil.mealrecommendation.exception.ResourceNotFoundException;
import com.mukil.mealrecommendation.interfaces.NutritionAnalysisService;
import com.mukil.mealrecommendation.model.RecipeNutrition;
import com.mukil.mealrecommendation.util.CommonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class EdamamServiceImpl implements NutritionAnalysisService {

    @Value("${api.edamam.app-id}")
    private String appId;

    @Value("${api.edamam.app-key}")
    private String appKey;

    @Value("${api.edamam.recipe-search-url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public RecipeNutrition analyzeNutrition(String recipe) throws Exception {
        String requestUrl = String.format("%s?app_id=%s&app_key=%s&ingr=%s", apiUrl, appId, appKey, recipe);

        Map<String, Object> nutritionData = restTemplate.getForObject(requestUrl, HashMap.class);

        if (nutritionData == null || nutritionData.isEmpty()) {
            throw new ResourceNotFoundException("Nutirition data is not available by this time.");
        }
        return CommonUtils.mapToRecipeNutrition(nutritionData, recipe);
    }
}
