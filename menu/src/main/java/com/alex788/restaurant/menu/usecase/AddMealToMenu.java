package com.alex788.restaurant.menu.usecase;

import com.alex788.restaurant.menu.domain.value_object.MealDescription;
import com.alex788.restaurant.menu.domain.value_object.MealId;
import com.alex788.restaurant.menu.domain.value_object.MealName;
import com.alex788.restaurant.menu.domain.value_object.MealPrice;
import com.alex788.restaurant.menu.usecase.error.AddMealToMenuUseCaseError;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

public interface AddMealToMenu {

    Either<AddMealToMenuUseCaseError, MealId> execute(AddMealToMenuRequest request);

    @Getter
    @EqualsAndHashCode
    @AllArgsConstructor
    class AddMealToMenuRequest {

        private final MealName name;
        private final MealDescription description;
        private final MealPrice price;
    }
}
