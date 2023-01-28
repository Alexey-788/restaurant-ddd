package com.alex788.restaurant.shop.usecase.error;

import com.alex788.restaurant.common.error.UseCaseError;

public interface AddMealToMenuUseCaseError extends UseCaseError {

    String access(ErrorMessageMapper errorMessageParser);

    class NameMustBeUniqueError implements AddMealToMenuUseCaseError {

        @Override
        public String access(ErrorMessageMapper errorMessageParser) {
            return errorMessageParser.visit(this);
        }
    }

    interface ErrorMessageMapper {

        String visit(NameMustBeUniqueError error);
    }
}
