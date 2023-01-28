package com.alex788.restaurant.shop.usecase.scenario;

import com.alex788.restaurant.shop.domain.Meal;
import com.alex788.restaurant.shop.domain.error.AddMealToMenuError;
import com.alex788.restaurant.shop.domain.invariant.MealNameIsUnique;
import com.alex788.restaurant.shop.domain.value_object.MealId;
import com.alex788.restaurant.shop.usecase.AddMealToMenu;
import com.alex788.restaurant.shop.usecase.access.MealPersister;
import com.alex788.restaurant.shop.usecase.error.AddMealToMenuErrorMapper;
import com.alex788.restaurant.shop.usecase.error.AddMealToMenuUseCaseError;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddMealToMenuUseCase implements AddMealToMenu {

    private final MealPersister persister;
    private final MealId.MealIdGenerator idGenerator;
    private final MealNameIsUnique nameIsUnique;

    private final AddMealToMenuErrorMapper errorMapper = new AddMealToMenuErrorMapper();

    @Override
    public Either<AddMealToMenuUseCaseError, MealId> execute(AddMealToMenuRequest request) {
        Either<AddMealToMenuError, Meal> mealEth = Meal.addToMenu(
                idGenerator,
                nameIsUnique,
                request.getName(),
                request.getDescription(),
                request.getPrice()
        );

        if (mealEth.isLeft()) {
            AddMealToMenuError domainError = mealEth.getLeft();
            AddMealToMenuUseCaseError useCaseError = (AddMealToMenuUseCaseError) domainError.accept(errorMapper);
            return Either.left(useCaseError);
        }

        Meal meal = mealEth.get();
        persister.save(meal);

        return Either.right(meal.getId());
    }
}
