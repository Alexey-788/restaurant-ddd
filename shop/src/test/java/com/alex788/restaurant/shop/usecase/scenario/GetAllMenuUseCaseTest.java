package com.alex788.restaurant.shop.usecase.scenario;

import com.alex788.restaurant.shop.domain.menu.Meal;
import com.alex788.restaurant.shop.usecase.access.MealExtracter;
import com.alex788.restaurant.shop.usecase.dto.MealInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.alex788.restaurant.shop.domain.menu.Fixtures.newMeal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class GetAllMenuUseCaseTest {

    GetAllMenuUseCase getAllMenuUseCase;
    MealExtracter mealExtracter;

    @BeforeEach
    void beforeEach() {
        mealExtracter = mock(MealExtracter.class);
        getAllMenuUseCase = new GetAllMenuUseCase(mealExtracter);
    }

    @Test
    void execute_ThreeMealsInMenu_ReturnsThreeMeals() {
        List<Meal> meals = List.of(newMeal(), newMeal(), newMeal());
        doReturn(meals).when(mealExtracter).getAll();

        List<MealInfo> mealInfos = getAllMenuUseCase.execute();

        assertEquals(meals.size(), mealInfos.size());
        for (int i = 0; i < meals.size(); i++) {
            assertTrue(mealEqualsMealInfo(meals.get(i), mealInfos.get(i)));
        }
    }

    boolean mealEqualsMealInfo(Meal meal, MealInfo mealInfo) {
        return meal.getId().equals(mealInfo.getId()) &&
                meal.getName().equals(mealInfo.getName()) &&
                meal.getDescription().equals(mealInfo.getDescription()) &&
                meal.getPrice().equals(mealInfo.getPrice());
    }

    @Test
    void execute_NoMealsInMenu_ReturnsEmptyList() {
        List<Meal> meals = Collections.emptyList();
        doReturn(meals).when(mealExtracter).getAll();

        List<MealInfo> mealInfos = getAllMenuUseCase.execute();

        assertEquals(0, mealInfos.size());
    }
}