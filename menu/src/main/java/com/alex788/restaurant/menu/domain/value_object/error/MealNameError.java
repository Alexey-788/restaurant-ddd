package com.alex788.restaurant.menu.domain.value_object.error;

import com.alex788.restaurant.common.error.DomainError;

public interface MealNameError extends DomainError {

    class BlankError implements MealNameError {
    }
}
