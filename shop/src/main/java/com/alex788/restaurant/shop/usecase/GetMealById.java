package com.alex788.restaurant.shop.usecase;

import com.alex788.restaurant.shop.domain.value_object.MealId;
import com.alex788.restaurant.shop.usecase.dto.MealInfo;
import com.alex788.restaurant.shop.usecase.error.GetMealByIdUseCaseError;
import io.vavr.control.Either;

public interface GetMealById {

    Either<GetMealByIdUseCaseError, MealInfo> execute(MealId id);
}
