package com.alex788.restaurant.menu.domain.value_object.error;

import com.alex788.restaurant.common.error.DomainError;

public interface MealDescriptionError extends DomainError {

    String accept(ErrorMessageMapper errorMessageMapper);

    class BlankError implements MealDescriptionError {

        @Override
        public String accept(ErrorMessageMapper errorMessageMapper) {
            return errorMessageMapper.visit(this);
        }
    }

    interface ErrorMessageMapper {

        String visit(BlankError error);
    }
}
