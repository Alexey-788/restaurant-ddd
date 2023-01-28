package com.alex788.restaurant.shop.usecase.scenario;

import com.alex788.restaurant.shop.domain.Meal;
import com.alex788.restaurant.shop.domain.value_object.MealId;
import com.alex788.restaurant.shop.usecase.GetMealById;
import com.alex788.restaurant.shop.usecase.access.MealExtracter;
import com.alex788.restaurant.shop.usecase.dto.MealInfo;
import com.alex788.restaurant.shop.usecase.error.GetMealByIdUseCaseError;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class GetMealByIdUseCase implements GetMealById {

    private final MealExtracter mealExtracter;

    @Override
    public Either<GetMealByIdUseCaseError, MealInfo> execute(MealId id) {
        Optional<Meal> mealOpt = mealExtracter.getById(id);

        if (mealOpt.isEmpty()) {
            return Either.left(new GetMealByIdUseCaseError.NotFoundError());
        }

        return Either.right(MealInfo.from(mealOpt.get()));
    }
}
