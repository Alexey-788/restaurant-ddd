package com.alex788.restaurant.menu.usecase.scenario;

import com.alex788.restaurant.menu.domain.Meal;
import com.alex788.restaurant.menu.domain.value_object.MealId;
import com.alex788.restaurant.menu.usecase.GetMealById;
import com.alex788.restaurant.menu.usecase.access.MealExtracter;
import com.alex788.restaurant.menu.usecase.dto.MealInfo;
import com.alex788.restaurant.menu.usecase.error.GetMealByIdUseCaseError;
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
