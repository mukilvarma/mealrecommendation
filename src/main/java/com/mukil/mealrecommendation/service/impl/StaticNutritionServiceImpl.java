package com.mukil.mealrecommendation.service.impl;

import com.mukil.mealrecommendation.interfaces.NutritionAnalysisService;
import com.mukil.mealrecommendation.model.RecipeNutrition;
import com.mukil.mealrecommendation.util.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StaticNutritionServiceImpl implements NutritionAnalysisService {

    @Override
    public RecipeNutrition analyzeNutrition(String recipe) throws Exception {
        return CommonUtils.mapToRecipeNutrition(getDummyNutritionData(), recipe);
    }

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
