package com.alex788.restaurant.shop.rest.endpoint.get_meal_by_id;

import com.alex788.restaurant.shop.usecase.error.GetMealByIdUseCaseError;

public class GetMealByIdErrorMessageMapper implements GetMealByIdUseCaseError.ErrorMessageMapper {

    @Override
    public String visit(GetMealByIdUseCaseError.NotFoundError error) {
        return "No meal found with provided id.";
    }
}
