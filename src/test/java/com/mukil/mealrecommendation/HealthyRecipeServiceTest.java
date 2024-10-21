package com.mukil.mealrecommendation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.mukil.mealrecommendation.factory.ServiceFactory;
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
import org.springframework.boot.test.context.SpringBootTest;

public class HealthyRecipeServiceTest {

    private HealthyRecipeService healthyRecipeService;

    @Mock
    private ServiceFactory serviceFactory; // Mock the factory

    @Mock
    private RestaurantSearchService mockRestaurantService; // Mock the restaurant search service

    @Mock
    private NutritionAnalysisService mockNutritionAnalysisService; // Mock the nutrition analysis service

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetHealthyRecipes_UsingRealServices() throws Exception {
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

        healthyRecipeService = new HealthyRecipeService(serviceFactory, false);

        // Mocking factory behavior to return real services
        when(serviceFactory.getRestaurantService(false)).thenReturn(mockRestaurantService);
        when(serviceFactory.getNutritionService(false)).thenReturn(mockNutritionAnalysisService);

        // Mocking service behavior
        when(mockRestaurantService.searchRestaurantsByCuisineAndCity("Italian", "City A")).thenReturn(List.of(restaurant));
        when(mockRestaurantService.getMenuItemsForRestaurant(restaurant.getPlaceId())).thenReturn(menuItems);

        // Create expected NutritionInfo response
        RecipeNutrition expectedNutritionInfo = new RecipeNutrition();
        expectedNutritionInfo.setCalories(350);
        expectedNutritionInfo.setProtein(30.5);
        expectedNutritionInfo.setFat(10.0);
        expectedNutritionInfo.setCarbohydrates(20.0);

        // Mock the behavior of the analyzeNutrition method
        when(mockNutritionAnalysisService.analyzeNutrition(anyString())).thenReturn(expectedNutritionInfo);

        // Call the method
        List<RecipeNutrition> healthyRecipes = healthyRecipeService.getTopHealthyItems("Italian", "City A");

        // Verify results
        assertNotNull(healthyRecipes);
        assertEquals(2, healthyRecipes.size());

        // Verify interaction
        verify(serviceFactory).getRestaurantService(false);
        verify(mockRestaurantService).searchRestaurantsByCuisineAndCity("Italian", "City A");
        verify(mockRestaurantService).getMenuItemsForRestaurant(restaurant.getPlaceId());
        verify(mockNutritionAnalysisService, times(2)).analyzeNutrition(anyString());
    }

    @Test
    public void testGetHealthyRecipes_UsingMockServices() throws Exception {
        // Mock restaurant and menu items
        Restaurant restaurant = new Restaurant("2", "Mock Healthy Diner", "City B", "Mock Cuisine");
        MenuItem menuItem1 = new MenuItem("Mock Salad", "Mock Fresh salad", 20);
        MenuItem menuItem2 = new MenuItem("Mock Grilled Chicken", "Mock Grilled chicken breast", 30);
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(menuItem1);
        menuItems.add(menuItem2);

        // Mocking factory behavior to return mock services
        when(serviceFactory.getRestaurantService(true)).thenReturn(mockRestaurantService);
        when(serviceFactory.getNutritionService(true)).thenReturn(mockNutritionAnalysisService);

        // Mocking service behavior
        when(mockRestaurantService.searchRestaurantsByCuisineAndCity("Mock Cuisine", "City B")).thenReturn(List.of(restaurant));
        when(mockRestaurantService.getMenuItemsForRestaurant(restaurant.getPlaceId())).thenReturn(menuItems);

        // Mock the behavior of the analyzeNutrition method
        RecipeNutrition mockNutritionInfo = new RecipeNutrition("Mock Salad", "Mock Fresh salad", 100, 5.0, 2.0, 10.0);
        when(mockNutritionAnalysisService.analyzeNutrition(anyString())).thenReturn(mockNutritionInfo);

        healthyRecipeService = new HealthyRecipeService(serviceFactory, true);

        // Call the method
        List<RecipeNutrition> healthyRecipes = healthyRecipeService.getTopHealthyItems("Mock Cuisine", "City B");

        // Verify results
        assertNotNull(healthyRecipes);
        assertEquals(2, healthyRecipes.size());

        // Verify interaction
        verify(serviceFactory).getRestaurantService(true);
        verify(mockRestaurantService).searchRestaurantsByCuisineAndCity("Mock Cuisine", "City B");
        verify(mockRestaurantService).getMenuItemsForRestaurant(restaurant.getPlaceId());
        verify(mockNutritionAnalysisService, times(2)).analyzeNutrition(anyString());
    }

    @Test
    public void testGetHealthyRecipes_NoRestaurants() throws Exception {
        // Mocking factory behavior
        when(serviceFactory.getRestaurantService(false)).thenReturn(mockRestaurantService);

        // Mocking behavior to return an empty list
        when(mockRestaurantService.searchRestaurantsByCuisineAndCity(anyString(), anyString())).thenReturn(new ArrayList<>());

        // Call the method
        healthyRecipeService = new HealthyRecipeService(serviceFactory, false);

        // Call the method
        List<RecipeNutrition> healthyRecipes = healthyRecipeService.getTopHealthyItems("Italian", "City A");

        // Verify results
        assertNotNull(healthyRecipes);
        assertEquals(0, healthyRecipes.size());

        // Verify interaction
        verify(serviceFactory).getRestaurantService(false);
        verify(mockRestaurantService).searchRestaurantsByCuisineAndCity("Italian", "City A");
        verify(mockRestaurantService, never()).getMenuItemsForRestaurant(anyString());
        verify(mockNutritionAnalysisService, never()).analyzeNutrition(anyString());
    }

    @Test
    public void testGetHealthyRecipes_NutritionServiceFailure() throws Exception {
        // Mock restaurant and menu items
        Restaurant restaurant = new Restaurant("1", "Healthy Eats", "City A", "Italian");
        MenuItem menuItem1 = new MenuItem("Salad", "Fresh salad", 20);
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(menuItem1);

        // Mocking factory behavior to return real services
        when(serviceFactory.getRestaurantService(false)).thenReturn(mockRestaurantService);
        when(serviceFactory.getNutritionService(false)).thenReturn(mockNutritionAnalysisService);

        // Mocking service behavior
        when(mockRestaurantService.searchRestaurantsByCuisineAndCity("Italian", "City A")).thenReturn(List.of(restaurant));
        when(mockRestaurantService.getMenuItemsForRestaurant(restaurant.getPlaceId())).thenReturn(menuItems);

        // Mock failure in nutrition analysis
        when(mockNutritionAnalysisService.analyzeNutrition(anyString())).thenThrow(new RuntimeException("Nutrition service error"));

        // Initialize the HealthyRecipeService with the real services
        healthyRecipeService = new HealthyRecipeService(serviceFactory, false);

        // Call the method and assert that a RuntimeException is thrown
        Exception exception = assertThrows(RuntimeException.class, () -> {
            healthyRecipeService.getTopHealthyItems("Italian", "City A");
        });

        // Verify the exception message
        assertEquals("Nutrition service error", exception.getMessage());

        // Verify interaction
        verify(serviceFactory).getRestaurantService(false);
        verify(mockRestaurantService).searchRestaurantsByCuisineAndCity("Italian", "City A");
        verify(mockRestaurantService).getMenuItemsForRestaurant(restaurant.getPlaceId());
        verify(mockNutritionAnalysisService).analyzeNutrition(anyString());
    }

}
