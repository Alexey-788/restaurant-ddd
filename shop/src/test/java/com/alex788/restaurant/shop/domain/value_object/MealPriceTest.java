package com.alex788.restaurant.shop.domain.value_object;

import com.alex788.restaurant.shop.domain.value_object.error.MealPriceError;
import io.vavr.control.Either;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MealPriceTest {

    @ParameterizedTest
    @ValueSource(strings = {"2.14", "154", "15.7"})
    void from_WithValidValue_CreatesSuccessfully(String valueInString) {
        BigDecimal value = new BigDecimal(valueInString);

        Either<MealPriceError, MealPrice> mealPriceEth = MealPrice.from(value);

        assertTrue(mealPriceEth.isRight());
        MealPrice mealPrice = mealPriceEth.get();
        assertEquals(value, mealPrice.getValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"84.001", "100.1234"})
    void from_WithWrongScaleValue_ReturnsError(String valueInString) {
        BigDecimal value = new BigDecimal(valueInString);

        Either<MealPriceError, MealPrice> mealPriceEth = MealPrice.from(value);

        assertTrue(mealPriceEth.isLeft());
        assertInstanceOf(MealPriceError.WrongScaleError.class, mealPriceEth.getLeft());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-84.41", "-100.34"})
    void from_WithNegativeValue_ReturnsError(String valueInString) {
        BigDecimal value = new BigDecimal(valueInString);

        Either<MealPriceError, MealPrice> mealPriceEth = MealPrice.from(value);

        assertTrue(mealPriceEth.isLeft());
        assertInstanceOf(MealPriceError.NegativeError.class, mealPriceEth.getLeft());
    }
}