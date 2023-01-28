package com.alex788.restaurant.shop.usecase.scenario;

import com.alex788.restaurant.shop.domain.menu.Meal;
import com.alex788.restaurant.shop.domain.menu.value_object.MealId;
import com.alex788.restaurant.shop.usecase.AddMealToMenu;
import com.alex788.restaurant.shop.usecase.error.AddMealToMenuUseCaseError;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.alex788.restaurant.shop.domain.Fixtures.*;
import static com.alex788.restaurant.shop.usecase.Fixtures.TestMealPersister;
import static org.junit.jupiter.api.Assertions.*;

class AddMealToMenuUseCaseTest {

    TestMealIdGenerator idGenerator;
    TestMealPersister persister;

    @BeforeEach
    void beforeEach() {
        persister = new TestMealPersister();
        idGenerator = new TestMealIdGenerator();
    }

    @Test
    void execute_InInvariant_PersistMeal() {
        AddMealToMenuUseCase useCase = new AddMealToMenuUseCase(persister, idGenerator, mealNameIsUnique());

        AddMealToMenu.AddMealToMenuRequest request = new AddMealToMenu.AddMealToMenuRequest(
                mealName(),
                mealDescription(),
                mealPrice()
        );

        Either<AddMealToMenuUseCaseError, MealId> mealIdEth = useCase.execute(request);

        assertEquals(1, persister.storage.size());
        Meal storedMeal = persister.storage.get(0);
        assertEquals(idGenerator.mealId, storedMeal.getId());
        assertEquals(request.getName(), storedMeal.getName());
        assertEquals(request.getDescription(), storedMeal.getDescription());
        assertEquals(request.getPrice(), storedMeal.getPrice());
    }

    @Test
    void execute_InInvariant_ReturnsIdFromIdGenerator() {
        AddMealToMenuUseCase useCase = new AddMealToMenuUseCase(persister, idGenerator, mealNameIsUnique());

        AddMealToMenu.AddMealToMenuRequest request = new AddMealToMenu.AddMealToMenuRequest(
                mealName(),
                mealDescription(),
                mealPrice()
        );

        Either<AddMealToMenuUseCaseError, MealId> mealIdEth = useCase.execute(request);

        assertTrue(mealIdEth.isRight());
        assertEquals(idGenerator.mealId, mealIdEth.get());
    }

    @Test
    void execute_OutOfInvariant_ReturnsError() {
        AddMealToMenuUseCase useCase = new AddMealToMenuUseCase(persister, idGenerator, mealNameNotUnique());

        Either<AddMealToMenuUseCaseError, MealId> mealIdEth = useCase.execute(new AddMealToMenu.AddMealToMenuRequest(
                mealName(),
                mealDescription(),
                mealPrice()
        ));

        assertTrue(mealIdEth.isLeft());
        assertInstanceOf(AddMealToMenuUseCaseError.NameMustBeUniqueError.class, mealIdEth.getLeft());
    }
}