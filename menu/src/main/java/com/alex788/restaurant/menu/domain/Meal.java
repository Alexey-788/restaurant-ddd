package com.alex788.restaurant.menu.domain;

import com.alex788.restaurant.menu.domain.error.AddMealToMenuError;
import com.alex788.restaurant.menu.domain.invariant.MealNameIsUnique;
import com.alex788.restaurant.menu.domain.value_object.MealDescription;
import com.alex788.restaurant.menu.domain.value_object.MealId;
import com.alex788.restaurant.menu.domain.value_object.MealName;
import com.alex788.restaurant.menu.domain.value_object.MealPrice;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Meal {

    private MealId id;
    private MealName name;
    private MealDescription description;
    private MealPrice price;

    public static Either<AddMealToMenuError, Meal> addToMenu(
            MealId.MealIdGenerator idGenerator,
            MealNameIsUnique nameIsUnique,
            MealName name,
            MealDescription description,
            MealPrice price
    ) {
        if (!nameIsUnique.check(name)) {
            return Either.left(new AddMealToMenuError.NameMustBeUniqueError());
        }

        return Either.right(
                new Meal(
                        idGenerator.generate(),
                        name,
                        description,
                        price
                )
        );
    }
}
