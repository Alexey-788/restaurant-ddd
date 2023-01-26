package com.alex788.restaurant.menu.usecase;

import com.alex788.restaurant.menu.domain.value_object.MealId;
import com.alex788.restaurant.menu.usecase.dto.MealInfo;
import com.alex788.restaurant.menu.usecase.error.GetMealByIdUseCaseError;
import io.vavr.control.Either;

public interface GetMealById {

    Either<GetMealByIdUseCaseError, MealInfo> execute(MealId id);
}
