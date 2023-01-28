package com.alex788.restaurant.shop.domain.menu.value_object.error;

import com.alex788.restaurant.common.error.DomainError;

public interface MealNameError extends DomainError {

    String accept(ErrorMessageMapper errorMessageMapper);

    class BlankError implements MealNameError {

        @Override
        public String accept(ErrorMessageMapper errorMessageMapper) {
            return errorMessageMapper.visit(this);
        }
    }

    interface ErrorMessageMapper {

        String visit(BlankError error);
    }
}
