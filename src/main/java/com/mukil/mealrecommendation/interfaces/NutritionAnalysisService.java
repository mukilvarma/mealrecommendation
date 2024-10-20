package com.mukil.mealrecommendation.interfaces;

import com.mukil.mealrecommendation.model.RecipeNutrition;

public interface NutritionAnalysisService {
    RecipeNutrition analyzeNutrition(String recipe) throws Exception;
}

