package com.alex788.restaurant.menu.domain.value_object;

import io.vavr.control.Either;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class MealDescriptionTest {

    @ParameterizedTest
    @ValueSource(strings = {"Description", "Long description"})
    void from_WithValidValue_CreatesSuccessfully(String value) {
        Either<MealDescription.Error, MealDescription> mealDescriptionEth = MealDescription.from(value);

        assertTrue(mealDescriptionEth.isRight());
        MealDescription mealDescription = mealDescriptionEth.get();
        assertEquals(value, mealDescription.getValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "\n \t"})
    void from_WithBlankValue_ReturnsError(String value) {
        Either<MealDescription.Error, MealDescription> mealDescriptionEth = MealDescription.from(value);

        assertTrue(mealDescriptionEth.isLeft());
        assertInstanceOf(MealDescription.BlankError.class, mealDescriptionEth.getLeft());
    }
}