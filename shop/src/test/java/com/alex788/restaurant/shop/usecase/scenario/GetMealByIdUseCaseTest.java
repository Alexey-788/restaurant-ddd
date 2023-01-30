package com.alex788.restaurant.shop.usecase.scenario;

import com.alex788.restaurant.shop.domain.menu.Meal;
import com.alex788.restaurant.shop.domain.menu.value_object.MealId;
import com.alex788.restaurant.shop.usecase.access.MealExtracter;
import com.alex788.restaurant.shop.usecase.dto.MealInfo;
import com.alex788.restaurant.shop.usecase.error.GetMealByIdUseCaseError;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.alex788.restaurant.shop.domain.menu.Fixtures.mealId;
import static com.alex788.restaurant.shop.domain.menu.Fixtures.newMeal;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class GetMealByIdUseCaseTest {

    MealExtracter mealExtracter;
    GetMealByIdUseCase getMealByIdUseCase;

    @BeforeEach
    void beforeEach() {
        mealExtracter = mock(MealExtracter.class);
        getMealByIdUseCase = new GetMealByIdUseCase(mealExtracter);
    }

    @Test
    void execute_MealWithProvidedIdExists_ReturnsMeal() {
        Meal meal = newMeal();
        doReturn(Optional.of(meal)).when(mealExtracter).getById(meal.getId());

        Either<GetMealByIdUseCaseError, MealInfo> mealInfoEth = getMealByIdUseCase.execute(meal.getId());

        assertTrue(mealInfoEth.isRight());
        MealInfo mealInfo = mealInfoEth.get();
        assertEquals(meal.getId(), mealInfo.getId());
        assertEquals(meal.getName(), mealInfo.getName());
        assertEquals(meal.getDescription(), mealInfo.getDescription());
        assertEquals(meal.getPrice(), mealInfo.getPrice());
    }

    @Test
    void execute_MealWithProvidedIdNotExists_ReturnsError() {
        MealId id = mealId();
        doReturn(Optional.empty()).when(mealExtracter).getById(id);

        Either<GetMealByIdUseCaseError, MealInfo> mealInfoEth = getMealByIdUseCase.execute(id);

        assertTrue(mealInfoEth.isLeft());
        assertInstanceOf(GetMealByIdUseCaseError.NotFoundError.class, mealInfoEth.getLeft());
    }
}