package com.alex788.restaurant.shop.domain.value_object;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MealIdTest {

    @ParameterizedTest
    @ValueSource(longs = {-123, 0, 123})
    void constructor_WithVariousValues_CreatesSuccessfully(long value) {
        MealId mealId = new MealId(value);

        assertEquals(value, mealId.getValue());
    }
}