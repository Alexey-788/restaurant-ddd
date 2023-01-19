package com.alex788.restaurant.menu.domain.value_object.error;

import com.alex788.restaurant.common.error.DomainError;

public interface MealPriceError extends DomainError {

    class WrongScaleError implements MealPriceError {
    }

    class NegativeError implements MealPriceError {
    }
}
