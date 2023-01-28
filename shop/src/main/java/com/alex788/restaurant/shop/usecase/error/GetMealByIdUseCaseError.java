package com.alex788.restaurant.shop.usecase.error;

import com.alex788.restaurant.common.error.UseCaseError;

public interface GetMealByIdUseCaseError extends UseCaseError {

    String accept(ErrorMessageMapper errorMessageMapper);

    class NotFoundError implements GetMealByIdUseCaseError {

        @Override
        public String accept(ErrorMessageMapper errorMessageMapper) {
            return errorMessageMapper.visit(this);
        }
    }

    interface ErrorMessageMapper {

        String visit(NotFoundError error);
    }
}
