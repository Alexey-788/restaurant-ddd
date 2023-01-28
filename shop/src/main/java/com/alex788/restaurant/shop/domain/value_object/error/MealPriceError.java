package com.alex788.restaurant.shop.domain.value_object.error;

import com.alex788.restaurant.common.error.DomainError;

public interface MealPriceError extends DomainError {

    String accept(ErrorMessageMapper errorMessageMapper);

    class WrongScaleError implements MealPriceError {

        @Override
        public String accept(ErrorMessageMapper errorMessageMapper) {
            return errorMessageMapper.visit(this);
        }
    }

    class NegativeError implements MealPriceError {

        @Override
        public String accept(ErrorMessageMapper errorMessageMapper) {
            return errorMessageMapper.visit(this);
        }
    }

    interface ErrorMessageMapper {

        String visit(WrongScaleError error);
        String visit(NegativeError error);
    }
}
