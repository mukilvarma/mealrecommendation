package com.mukil.mealrecommendation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@OpenAPIDefinition(
		info = @Info(title = "Meal Recommendation API", version = "1.0", description = "API for recommending healthy meals from restaurants"),
		servers = @Server(url = "http://localhost:8080")
)
@SpringBootApplication
@EnableCaching
public class MealrecommendationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MealrecommendationApplication.class, args);
	}

}
