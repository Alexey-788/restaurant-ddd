package com.alex788.restaurant.menu.rest.endpoint.get_meal_by_id;

import com.alex788.restaurant.menu.usecase.error.GetMealByIdUseCaseError;

public class GetMealByIdErrorMessageMapper implements GetMealByIdUseCaseError.ErrorMessageMapper {

    @Override
    public String visit(GetMealByIdUseCaseError.NotFoundError error) {
        return "No meal found with provided id.";
    }
}
