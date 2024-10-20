package com.mukil.mealrecommendation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.mukil.mealrecommendation.interfaces.NutritionAnalysisService;
import com.mukil.mealrecommendation.interfaces.RestaurantSearchService;
import com.mukil.mealrecommendation.model.MenuItem;
import com.mukil.mealrecommendation.model.RecipeNutrition;
import com.mukil.mealrecommendation.model.Restaurant;
import com.mukil.mealrecommendation.service.HealthyRecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class HealthyRecipeServiceTest {

    @InjectMocks
    private HealthyRecipeService healthyRecipeService;

    @Mock
    private RestaurantSearchService restaurantService; // Mock the service that fetches restaurants

    @Mock
    private NutritionAnalysisService nutritionAnalysisService; // Mock the service that analyse nutrition

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetHealthyRecipes()  throws Exception {
        // Mock restaurant and menu items
        Restaurant restaurant = new Restaurant("1", "Healthy Eats", "City A", "Italian");
        RecipeNutrition item1 = new RecipeNutrition("Salad", "Fresh salad", 200, 5.0, 10.0, 15.0);
        RecipeNutrition item2 = new RecipeNutrition("Grilled Chicken", "Grilled chicken breast", 300, 25.0, 10.0, 0.0);
        List<RecipeNutrition> recipeNutritions = new ArrayList<>();
        recipeNutritions.add(item1);
        recipeNutritions.add(item2);

        MenuItem menuItem1 = new MenuItem("Salad", "Fresh salad", 20);
        MenuItem menuItem2 = new MenuItem("Grilled Chicken", "Grilled chicken breast", 30);
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(menuItem1);
        menuItems.add(menuItem2);

        // Mocking behavior
        when(restaurantService.searchRestaurantsByCuisineAndCity("Italian", "City A")).thenReturn(List.of(restaurant));
        when(restaurantService.getMenuItemsForRestaurant(restaurant.getPlaceId())).thenReturn(menuItems);

        //MenuItem menuItem = new MenuItem("Grilled Chicken Salad", "A healthy salad with grilled chicken and mixed greens.", 12.99, 350, 30.5, 10.0, 20.0);

        // Create expected NutritionInfo response
        RecipeNutrition expectedNutritionInfo = new RecipeNutrition();
        expectedNutritionInfo.setCalories(350);
        expectedNutritionInfo.setProtein(30.5);
        expectedNutritionInfo.setFat(10.0);
        expectedNutritionInfo.setCarbohydrates(20.0);

        // Mock the behavior of the analyzeNutrition method
        when(nutritionAnalysisService.analyzeNutrition(any(String.class))).thenReturn(expectedNutritionInfo);


        // Call the method
        List<RecipeNutrition> healthyRecipes = healthyRecipeService.getTopHealthyItems("Italian", "City A");

        // Verify results
        assertNotNull(healthyRecipes);
        assertEquals(2, healthyRecipes.size());
        System.out.println(healthyRecipes.get(0).getName());
  //      assertEquals("Grilled Chicken", healthyRecipes.get(0).getName());

        // Verify interaction
        verify(restaurantService).searchRestaurantsByCuisineAndCity("Italian", "City A");
        verify(restaurantService).getMenuItemsForRestaurant(restaurant.getPlaceId());
    }

    @Test
    public void testGetHealthyRecipes_NoRestaurants() throws Exception {
        // Mocking behavior to return an empty list
        when(restaurantService.searchRestaurantsByCuisineAndCity(anyString(), anyString())).thenReturn(new ArrayList<>());

        // Call the method
        List<RecipeNutrition> healthyRecipes = healthyRecipeService.getTopHealthyItems("Italian", "City A");

        // Verify results
        assertNotNull(healthyRecipes);
        assertEquals(0, healthyRecipes.size());

        // Verify interaction
        verify(restaurantService).searchRestaurantsByCuisineAndCity("Italian", "City A");
        verify(restaurantService, never()).getMenuItemsForRestaurant(anyString());
    }

    // Add more test cases as needed
}
