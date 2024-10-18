package com.mukil.mealrecommendation.interfaces;

import com.mukil.mealrecommendation.model.MenuItem;

public interface NutritionAnalysisService {
    MenuItem analyzeNutrition(MenuItem menuItem) throws Exception;
}

