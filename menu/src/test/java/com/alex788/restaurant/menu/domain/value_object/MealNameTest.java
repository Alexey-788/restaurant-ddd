package com.alex788.restaurant.menu.domain.value_object;

import io.vavr.control.Either;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class MealNameTest {

    @ParameterizedTest
    @ValueSource(strings = {"Name", "Full Name"})
    void from_WithValidValue_CreatesSuccessfully(String value) {
        Either<MealName.Error, MealName> mealNameEth = MealName.from(value);

        assertTrue(mealNameEth.isRight());
        MealName mealName = mealNameEth.get();
        assertEquals(value, mealName.getValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "\n \t"})
    void from_WithBlankValue_ReturnsError(String value) {
        Either<MealName.Error, MealName> mealNameEth = MealName.from(value);

        assertTrue(mealNameEth.isLeft());
        assertInstanceOf(MealName.BlankError.class, mealNameEth.getLeft());
    }
}