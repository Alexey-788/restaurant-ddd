package com.alex788.restaurant.menu.domain;

import com.alex788.restaurant.menu.domain.error.AddMealToMenuError;
import com.alex788.restaurant.menu.domain.invariant.MealNameIsUnique;
import com.alex788.restaurant.menu.domain.value_object.MealDescription;
import com.alex788.restaurant.menu.domain.value_object.MealId;
import com.alex788.restaurant.menu.domain.value_object.MealName;
import com.alex788.restaurant.menu.domain.value_object.MealPrice;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import static com.alex788.restaurant.menu.domain.value_object.Fixtures.*;
import static org.junit.jupiter.api.Assertions.*;

class MealTest {

    @Test
    void addToMenu_InInvariant_ReturnsMeal() {
        MealId mealId = mealId();
        MealName mealName = mealName();
        MealDescription mealDescription = mealDescription();
        MealPrice mealPrice = mealPrice();

        Either<AddMealToMenuError, Meal> mealEth = Meal.addToMenu(
                mealIdGeneratorThatGenerate(mealId),
                mealNameIsUnique(),
                mealName,
                mealDescription,
                mealPrice
        );

        assertTrue(mealEth.isRight());
        Meal meal = mealEth.get();
        assertEquals(mealId, meal.getId());
        assertEquals(mealName, meal.getName());
        assertEquals(mealDescription, meal.getDescription());
        assertEquals(mealPrice, meal.getPrice());
    }

    @Test
    void addToMenu_MealWithSameNameExists_ReturnsError() {
        Either<AddMealToMenuError, Meal> mealEth = Meal.addToMenu(
                mealIdGeneratorThatGenerate(mealId()),
                mealNameNotUnique(),
                mealName(),
                mealDescription(),
                mealPrice()
        );

        assertTrue(mealEth.isLeft());
        assertInstanceOf(AddMealToMenuError.NameMustBeUniqueError.class, mealEth.getLeft());
    }

    MealId.MealIdGenerator mealIdGeneratorThatGenerate(MealId id) {
        return () -> id;
    }

    MealNameIsUnique mealNameIsUnique() {
        return (name) -> true;
    }

    MealNameIsUnique mealNameNotUnique() {
        return (name) -> false;
    }
}