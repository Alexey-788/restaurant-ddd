package com.alex788.restaurant.menu.rest.endpoint.add_meal_to_menu;

import com.alex788.restaurant.menu.usecase.error.AddMealToMenuUseCaseError;

public class AddMealToMenuErrorMessageMapper implements AddMealToMenuUseCaseError.ErrorMessageMapper {

    @Override
    public String visit(AddMealToMenuUseCaseError.NameMustBeUniqueError error) {
        return "Meal with same name already exists.";
    }
}
