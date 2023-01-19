package com.alex788.restaurant.menu.error;

import com.alex788.restaurant.common.error.UseCaseError;
import com.alex788.restaurant.menu.domain.error.AddMealToMenuError;

public interface AddMealToMenuUseCaseError extends UseCaseError {

    class NameMustBeUniqueError implements AddMealToMenuUseCaseError {
    }

    class ErrorMapper implements AddMealToMenuError.ErrorMapper {

        @Override
        public AddMealToMenuUseCaseError visit(AddMealToMenuError.NameMustBeUniqueError error) {
            return new AddMealToMenuUseCaseError.NameMustBeUniqueError();
        }
    }
}
