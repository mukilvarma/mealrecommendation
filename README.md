
# Meal Recommendation Backend

This is the backend service for the Meal Recommendation system, built using Spring Boot. It fetches restaurant information and provides healthy meal recommendations.

## Features

- Search restaurants by cuisine type and city.
- Recommend top 10 healthy meals from restaurant menus.
- Uses mock data if restaurant data is unavailable.

## Technologies

- Java 21
- Spring Boot
- Maven
- Google Places API (optional)

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/mukilvarma/mealrecommendation.git
cd mealrecommendation
```

### 2. Set Up Google API Key

Create a `.env` file in the project root and add your Google Places API key:

```bash
GOOGLE_API_KEY=your-google-api-key
```

### 3. Run the Application

#### With Maven

```bash
mvn clean install
mvn spring-boot:run
```

#### With Docker

```bash
docker build -t mealrecommendation-backend .
docker run -p 8080:8080 --env-file .env mealrecommendation-backend
```

### 4. API Endpoints

- **Search Restaurants by Cuisine and City:**
  
  `GET /restaurants?cuisineType={cuisineType}&city={city}`

- **Top 10 Healthy Meal Recommendations:**
  
  `GET /restaurants/{placeId}/menu/recommendations`

### 5. Running Tests

```bash
mvn test
```

## License

This project is licensed under the MIT License.
