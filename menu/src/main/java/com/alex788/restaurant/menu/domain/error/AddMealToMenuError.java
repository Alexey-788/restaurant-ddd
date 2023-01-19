package com.alex788.restaurant.menu.domain.error;

import com.alex788.restaurant.common.error.DomainError;

public interface AddMealToMenuError extends DomainError {

    class NameMustBeUniqueError implements AddMealToMenuError {
    }
}
