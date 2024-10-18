package com.mukil.mealrecommendation.model;

public class MenuItem {

    private String name;          // Name of the menu item
    private String description;   // Description of the menu item
    private double price;         // Price of the menu item
    private int calories;         // Calories in the menu item
    private double protein;       // Protein content in grams
    private double fat;           // Fat content in grams
    private double carbohydrates;  // Carbohydrates content in grams

    // Default Constructor
    public MenuItem() {
    }

    // Constructor to initialize all fields
    public MenuItem(String name, String description, double price, int calories, double protein, double fat, double carbohydrates) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCalories() {
        return calories; // This method should return the calories
    }

    public void setCalories(int calories) {
        this.calories = calories; // This method should allow setting calories
    }

    public double getProtein() {
        return protein; // This method returns protein content
    }

    public void setProtein(double protein) {
        this.protein = protein; // This method allows setting protein content
    }

    public double getFat() {
        return fat; // This method returns fat content
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbohydrates() {
        return carbohydrates; // This method returns carbohydrates content
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }
}