package com.alex788.restaurant.menu.usecase.scenario;

import com.alex788.restaurant.menu.access.MealPersister;
import com.alex788.restaurant.menu.domain.Meal;
import com.alex788.restaurant.menu.domain.invariant.MealNameIsUnique;
import com.alex788.restaurant.menu.domain.value_object.MealId;
import com.alex788.restaurant.menu.usecase.AddMealToMenu;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddMealToMenuUseCase implements AddMealToMenu {

    private final MealPersister persister;
    private final MealId.MealIdGenerator idGenerator;
    private final MealNameIsUnique nameIsUnique;

    @Override
    public Either<UseCaseError, MealId> execute(AddMealToMenuRequest request) {
        Either<Meal.InvariantError, Meal> mealEth = Meal.addToMenu(
                idGenerator,
                nameIsUnique,
                request.getName(),
                request.getDescription(),
                request.getPrice()
        );

        if (mealEth.isLeft()) {
            Meal.InvariantError error = mealEth.getLeft();
            if (error instanceof Meal.NameMustBeUniqueError) {
                return Either.left(new UseCaseError());
            }
        }

        Meal meal = mealEth.get();
        persister.persist(meal);

        return Either.right(meal.getId());
    }
}
