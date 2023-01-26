package com.alex788.restaurant.menu.usecase.error;

import com.alex788.restaurant.menu.domain.error.AddMealToMenuError;

public class AddMealToMenuErrorMapper implements AddMealToMenuError.ErrorMapper {

    @Override
    public AddMealToMenuUseCaseError visit(AddMealToMenuError.NameMustBeUniqueError error) {
        return new AddMealToMenuUseCaseError.NameMustBeUniqueError();
    }
}
