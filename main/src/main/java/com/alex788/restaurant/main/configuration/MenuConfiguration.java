package com.alex788.restaurant.main.configuration;

import com.alex788.restaurant.shop.domain.menu.invariant.MealNameIsUnique;
import com.alex788.restaurant.shop.domain.menu.value_object.MealId;
import com.alex788.restaurant.shop.postgres_persistence.PostgresMealIdGenerator;
import com.alex788.restaurant.shop.postgres_persistence.PostgresMealRepository;
import com.alex788.restaurant.shop.rest.endpoint.add_meal_to_menu.AddMealToMenuEndpoint;
import com.alex788.restaurant.shop.rest.endpoint.get_all_menu.GetAllMenuEndpoint;
import com.alex788.restaurant.shop.rest.endpoint.get_meal_by_id.GetMealByIdEndpoint;
import com.alex788.restaurant.shop.usecase.AddMealToMenu;
import com.alex788.restaurant.shop.usecase.GetAllMenu;
import com.alex788.restaurant.shop.usecase.GetMealById;
import com.alex788.restaurant.shop.usecase.access.MealExtracter;
import com.alex788.restaurant.shop.usecase.access.MealPersister;
import com.alex788.restaurant.shop.usecase.invariant.MealNameIsUniqueImpl;
import com.alex788.restaurant.shop.usecase.scenario.AddMealToMenuUseCase;
import com.alex788.restaurant.shop.usecase.scenario.GetAllMenuUseCase;
import com.alex788.restaurant.shop.usecase.scenario.GetMealByIdUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MenuConfiguration {

    // UseCase
    @Bean
    public AddMealToMenu addMealToMenu(
            MealPersister mealPersister,
            MealId.MealIdGenerator mealIdGenerator,
            MealNameIsUnique mealNameIsUnique
    ) {
        return new AddMealToMenuUseCase(mealPersister, mealIdGenerator, mealNameIsUnique);
    }

    @Bean
    public GetAllMenu getAllMenu(MealExtracter mealExtracter) {
        return new GetAllMenuUseCase(mealExtracter);
    }

    @Bean
    public GetMealById getMealById(MealExtracter mealExtracter) {
        return new GetMealByIdUseCase(mealExtracter);
    }

    // Invariant
    @Bean
    public MealNameIsUnique mealNameIsUnique(MealExtracter mealExtracter) {
        return new MealNameIsUniqueImpl(mealExtracter);
    }

    // Repository & IdGenerator
    @Bean
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public PostgresMealRepository postgresMealRepository(DataSource dataSource) {
        return new PostgresMealRepository(dataSource);
    }

    @Bean
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public MealId.MealIdGenerator idGenerator(DataSource dataSource) {
        return new PostgresMealIdGenerator(dataSource);
    }

    // Endpoint
    @Bean
    public AddMealToMenuEndpoint addMealToMenuEndpoint(AddMealToMenu addMealToMenu) {
        return new AddMealToMenuEndpoint(addMealToMenu);
    }

    @Bean
    public GetAllMenuEndpoint getAllMenuEndpoint(GetAllMenu getAllMenu) {
        return new GetAllMenuEndpoint(getAllMenu);
    }

    @Bean
    GetMealByIdEndpoint getMealByIdEndpoint(GetMealById getMealById) {
        return new GetMealByIdEndpoint(getMealById);
    }
}
