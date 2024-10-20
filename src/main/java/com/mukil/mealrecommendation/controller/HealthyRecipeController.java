package com.mukil.mealrecommendation.controller;


import com.mukil.mealrecommendation.model.RecipeNutrition;
import com.mukil.mealrecommendation.service.HealthyRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/api/healthy-recipes")
@CrossOrigin(origins = "http://localhost:4200") // Allow requests from this origin
@Tag(name = "Recipe Controller", description = "APIs for managing recipes")
public class HealthyRecipeController {

    private final HealthyRecipeService healthyRecipeService;

    @Autowired
    public HealthyRecipeController(HealthyRecipeService healthyRecipeService) {
        this.healthyRecipeService = healthyRecipeService;
    }

    @GetMapping
    @Operation(summary = "Get healthy recipes")
    public ResponseEntity<List<RecipeNutrition>> getTopHealthyRecipes(
            @RequestParam String cuisineType, @RequestParam String city) throws Exception {

        List<RecipeNutrition> topHealthyItems = healthyRecipeService.getTopHealthyItems(cuisineType, city);
        return new ResponseEntity<>(topHealthyItems, HttpStatus.OK);
    }
}
