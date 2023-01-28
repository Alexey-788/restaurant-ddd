package com.alex788.restaurant.shop.domain.menu;

import com.alex788.restaurant.shop.domain.menu.error.AddMealToMenuError;
import com.alex788.restaurant.shop.domain.menu.invariant.MealNameIsUnique;
import com.alex788.restaurant.shop.domain.menu.value_object.MealDescription;
import com.alex788.restaurant.shop.domain.menu.value_object.MealId;
import com.alex788.restaurant.shop.domain.menu.value_object.MealName;
import com.alex788.restaurant.shop.domain.menu.value_object.MealPrice;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Meal {

    private final MealId id;
    private final MealName name;
    private final MealDescription description;
    private final MealPrice price;

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
