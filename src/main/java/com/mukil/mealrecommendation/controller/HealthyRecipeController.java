package com.mukil.mealrecommendation.controller;


import com.mukil.mealrecommendation.model.RecipeNutrition;
import com.mukil.mealrecommendation.service.HealthyRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

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
    @Cacheable(value = "nutritionCache", key = "#cuisineType + #city")
    public ResponseEntity<List<RecipeNutrition>> getTopHealthyRecipes(
            @RequestParam String cuisineType, @RequestParam String city) throws Exception {
        List<RecipeNutrition> topHealthyItems = healthyRecipeService.getTopHealthyItems(cuisineType, city);
        return new ResponseEntity<>(topHealthyItems, HttpStatus.OK);
    }

    @DeleteMapping("/clear-cache/{recipe}")
    @Operation(summary = "Clear cache for a specific recipe")
    @CacheEvict(value = "nutritionCache", key = "#recipe")
    public ResponseEntity<String> clearCacheForRecipe(@PathVariable String recipe) {
        return new ResponseEntity<>("Cache cleared for recipe: " + recipe, HttpStatus.OK);
    }

    @DeleteMapping("/clear-cache")
    @Operation(summary = "Clear all recipe caches")
    @CacheEvict(value = "nutritionCache", allEntries = true)
    public ResponseEntity<String> clearAllCache() {
        return new ResponseEntity<>("All recipe caches cleared", HttpStatus.OK);
    }
}
