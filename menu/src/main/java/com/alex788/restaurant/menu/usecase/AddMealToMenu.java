package com.alex788.restaurant.menu.usecase;

import com.alex788.restaurant.common.error.BusinessError;
import com.alex788.restaurant.menu.domain.value_object.MealDescription;
import com.alex788.restaurant.menu.domain.value_object.MealId;
import com.alex788.restaurant.menu.domain.value_object.MealName;
import com.alex788.restaurant.menu.domain.value_object.MealPrice;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface AddMealToMenu {

    Either<UseCaseError, MealId> execute(AddMealToMenuRequest request);

    @Getter
    @AllArgsConstructor
    class AddMealToMenuRequest {

        private final MealName name;
        private final MealDescription description;
        private final MealPrice price;
    }

    class UseCaseError implements BusinessError {
    }

    class NameMustBeUniqueUseCaseError extends UseCaseError {
    }
}
