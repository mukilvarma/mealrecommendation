package com.mukil.mealrecommendation.controller;

import com.mukil.mealrecommendation.model.MenuItem;
import com.mukil.mealrecommendation.service.HealthyRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/healthy-recipes")
public class HealthyRecipeController {

    private final HealthyRecipeService healthyRecipeService;

    @Autowired
    public HealthyRecipeController(HealthyRecipeService healthyRecipeService) {
        this.healthyRecipeService = healthyRecipeService;
    }

    @GetMapping
    public ResponseEntity<List<MenuItem>> getTopHealthyRecipes(
            @RequestParam String cuisineType, @RequestParam String city) throws Exception {

        List<MenuItem> topHealthyItems = healthyRecipeService.getTopHealthyItems(cuisineType, city);
        return new ResponseEntity<>(topHealthyItems, HttpStatus.OK);
    }
}
