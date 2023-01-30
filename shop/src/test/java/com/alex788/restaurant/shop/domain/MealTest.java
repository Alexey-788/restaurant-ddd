package com.alex788.restaurant.shop.domain;

import com.alex788.restaurant.shop.domain.menu.Fixtures.*;
import com.alex788.restaurant.shop.domain.menu.Meal;
import com.alex788.restaurant.shop.domain.menu.error.AddMealToMenuError;
import com.alex788.restaurant.shop.domain.menu.value_object.MealDescription;
import com.alex788.restaurant.shop.domain.menu.value_object.MealName;
import com.alex788.restaurant.shop.domain.menu.value_object.MealPrice;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import static com.alex788.restaurant.shop.domain.menu.Fixtures.*;
import static org.junit.jupiter.api.Assertions.*;

class MealTest {

    @Test
    void addToMenu_InInvariant_ReturnsMeal() {
        TestMealIdGenerator mealIdGenerator = new TestMealIdGenerator();
        MealName mealName = mealName();
        MealDescription mealDescription = mealDescription();
        MealPrice mealPrice = mealPrice();

        Either<AddMealToMenuError, Meal> mealEth = Meal.addToMenu(
                mealIdGenerator,
                mealNameIsUnique(),
                mealName,
                mealDescription,
                mealPrice
        );

        assertTrue(mealEth.isRight());
        Meal meal = mealEth.get();
        assertEquals(mealIdGenerator.mealId, meal.getId());
        assertEquals(mealName, meal.getName());
        assertEquals(mealDescription, meal.getDescription());
        assertEquals(mealPrice, meal.getPrice());
    }

    @Test
    void addToMenu_MealWithSameNameExists_ReturnsError() {
        Either<AddMealToMenuError, Meal> mealEth = Meal.addToMenu(
                new TestMealIdGenerator(),
                mealNameNotUnique(),
                mealName(),
                mealDescription(),
                mealPrice()
        );

        assertTrue(mealEth.isLeft());
        assertInstanceOf(AddMealToMenuError.NameMustBeUniqueError.class, mealEth.getLeft());
    }
}