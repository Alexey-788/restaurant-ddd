package com.alex788.restaurant.main.configuration;

import com.alex788.restaurant.menu.domain.invariant.MealNameIsUnique;
import com.alex788.restaurant.menu.domain.value_object.MealId;
import com.alex788.restaurant.menu.postgres_persistence.PostgresMealIdGenerator;
import com.alex788.restaurant.menu.postgres_persistence.PostgresMealRepository;
import com.alex788.restaurant.menu.rest.endpoint.add_meal_to_menu.AddMealToMenuEndpoint;
import com.alex788.restaurant.menu.usecase.AddMealToMenu;
import com.alex788.restaurant.menu.usecase.access.MealExtracter;
import com.alex788.restaurant.menu.usecase.access.MealPersister;
import com.alex788.restaurant.menu.usecase.invariant.MealNameIsUniqueImpl;
import com.alex788.restaurant.menu.usecase.scenario.AddMealToMenuUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MenuConfiguration {

    @Bean
    public AddMealToMenu addMealToMenu(
            MealPersister mealPersister,
            MealId.MealIdGenerator mealIdGenerator,
            MealNameIsUnique mealNameIsUnique
    ) {
        return new AddMealToMenuUseCase(mealPersister, mealIdGenerator, mealNameIsUnique);
    }

    @Bean
    public MealNameIsUnique mealNameIsUnique(MealExtracter mealExtracter) {
        return new MealNameIsUniqueImpl(mealExtracter);
    }

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

    @Bean
    public AddMealToMenuEndpoint addMealToMenuEndpoint(AddMealToMenu addMealToMenu) {
        return new AddMealToMenuEndpoint(addMealToMenu);
    }
}
