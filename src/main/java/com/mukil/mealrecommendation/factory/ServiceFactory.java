package com.mukil.mealrecommendation.factory;

import com.mukil.mealrecommendation.interfaces.NutritionAnalysisService;
import com.mukil.mealrecommendation.interfaces.RestaurantSearchService;
import com.mukil.mealrecommendation.service.impl.EdamamServiceImpl;
import com.mukil.mealrecommendation.service.impl.GooglePlacesServiceImpl;
import com.mukil.mealrecommendation.service.impl.StaticNutritionServiceImpl;
import com.mukil.mealrecommendation.service.impl.StaticRestaurantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceFactory {

    @Autowired
    private GooglePlacesServiceImpl googlePlacesService;

    @Autowired
    private EdamamServiceImpl edamamService;

    @Autowired
    private StaticRestaurantServiceImpl staticRestaurantService;

    @Autowired
    private StaticNutritionServiceImpl staticNutritionService;

    public RestaurantSearchService getRestaurantService(boolean useMockData) {
        return useMockData ? staticRestaurantService : googlePlacesService;
    }

    public NutritionAnalysisService getNutritionService(boolean useMockData) {
        return useMockData ? staticNutritionService : edamamService;
    }
}
