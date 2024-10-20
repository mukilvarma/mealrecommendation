package com.mukil.mealrecommendation.model;

public class Restaurant {

    private String name;        // Name of the restaurant
    private String address;     // Address of the restaurant
    private String cuisineType; // Type of cuisine the restaurant serves
    private String placeId;     // Unique identifier for the restaurant from Google Places

    // Default Constructor
    public Restaurant() {
    }

    // Constructor to initialize all fields
    public Restaurant(String name, String address, String cuisineType, String placeId) {
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.placeId = placeId; // Setting the placeId
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public String getPlaceId() {
        return placeId; // This method returns the placeId
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId; // This method allows setting the placeId
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", cuisineType='" + cuisineType + '\'' +
                ", placeId='" + placeId + '\'' +
                '}';
    }
}
