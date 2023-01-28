package com.alex788.restaurant.shop.domain.menu.error;

import com.alex788.restaurant.common.error.DomainError;
import com.alex788.restaurant.common.error.UseCaseError;

public interface AddMealToMenuError extends DomainError {

    UseCaseError accept(ErrorMapper errorMapper);

    class NameMustBeUniqueError implements AddMealToMenuError {

        @Override
        public UseCaseError accept(ErrorMapper errorMapper) {
            return errorMapper.visit(this);
        }
    }


    interface ErrorMapper {

        UseCaseError visit(NameMustBeUniqueError error);
    }
}
