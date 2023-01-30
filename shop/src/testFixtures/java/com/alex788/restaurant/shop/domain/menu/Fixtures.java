package com.alex788.restaurant.shop.domain.menu;

import com.alex788.restaurant.shop.domain.menu.invariant.MealNameIsUnique;
import com.alex788.restaurant.shop.domain.menu.value_object.MealDescription;
import com.alex788.restaurant.shop.domain.menu.value_object.MealId;
import com.alex788.restaurant.shop.domain.menu.value_object.MealName;
import com.alex788.restaurant.shop.domain.menu.value_object.MealPrice;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.alex788.restaurant.common.Fixtures.faker;

public class Fixtures {

    public static class TestMealIdGenerator implements MealId.MealIdGenerator {

        public final MealId mealId = mealId();

        @Override
        public MealId generate() {
            return mealId;
        }
    }

    public static MealId mealId() {
        long value = faker.number().randomNumber();
        return new MealId(value);
    }

    public static MealName mealName() {
        String value = faker.food().dish();
        return MealName.from(value).get();
    }

    public static MealDescription mealDescription() {
        String value = faker.lorem().sentence();
        return MealDescription.from(value).get();
    }

    public static MealPrice mealPrice() {
        String valueInString = faker.number().digits(5);
        BigDecimal value = new BigDecimal(valueInString).divide(new BigDecimal(100), MealPrice.SCALE, RoundingMode.UNNECESSARY);
        return MealPrice.from(value).get();
    }

    public static Meal newMeal() {
        return Meal.addToMenu(
                mealIdGeneratorThatReturns(mealId()),
                mealNameIsUnique(),
                mealName(),
                mealDescription(),
                mealPrice()
        ).get();
    }

    public static Meal newMeal(MealId mealId) {
        return Meal.addToMenu(
                mealIdGeneratorThatReturns(mealId),
                mealNameIsUnique(),
                mealName(),
                mealDescription(),
                mealPrice()
        ).get();
    }

    public static Meal newMeal(MealName mealName) {
        return Meal.addToMenu(
                mealIdGeneratorThatReturns(mealId()),
                mealNameIsUnique(),
                mealName,
                mealDescription(),
                mealPrice()
        ).get();
    }

    public static MealId.MealIdGenerator mealIdGeneratorThatReturns(MealId mealId) {
        return () -> mealId;
    }

    public static MealNameIsUnique mealNameIsUnique() {
        return name -> true;
    }

    public static MealNameIsUnique mealNameNotUnique() {
        return name -> false;
    }
}
