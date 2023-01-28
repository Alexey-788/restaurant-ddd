package com.alex788.restaurant.shop.rest.endpoint.add_meal_to_menu;

import com.alex788.restaurant.shop.domain.menu.value_object.MealDescription;
import com.alex788.restaurant.shop.domain.menu.value_object.MealName;
import com.alex788.restaurant.shop.domain.menu.value_object.MealPrice;
import com.alex788.restaurant.shop.rest.model.ErrorMessage;
import com.alex788.restaurant.shop.rest.model.MealModel;
import com.alex788.restaurant.shop.usecase.AddMealToMenu;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.alex788.restaurant.shop.domain.Fixtures.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AddMealToMenuRequestValidatorTest {

    AddMealToMenuRequestValidator validator;

    @BeforeEach
    void beforeEach() {
        validator = new AddMealToMenuRequestValidator();
    }

    @Test
    void validate_WithValidRequest_ReturnsUseCaseRequest() {
        MealName mealName = mealName();
        MealDescription mealDescription = mealDescription();
        MealPrice mealPrice = mealPrice();
        AddMealToMenuEndpoint.Request request =new AddMealToMenuEndpoint.Request(
                new MealModel(mealName.getValue(), mealDescription.getValue(), mealPrice.getValue())
        );

        Either<ErrorMessage, AddMealToMenu.AddMealToMenuRequest> useCaseRequestEth = validator.validate(request);

        assertTrue(useCaseRequestEth.isRight());
        AddMealToMenu.AddMealToMenuRequest useCaseRequest = useCaseRequestEth.get();
        assertEquals(mealName, useCaseRequest.getName());
        assertEquals(mealDescription, useCaseRequest.getDescription());
        assertEquals(mealPrice, useCaseRequest.getPrice());
    }

    @Test
    void validate_WithInvalidName_ReturnsErrorMessage() {
        String invalidName = "";
        MealDescription mealDescription = mealDescription();
        MealPrice mealPrice = mealPrice();
        AddMealToMenuEndpoint.Request request =new AddMealToMenuEndpoint.Request(
                new MealModel(invalidName, mealDescription.getValue(), mealPrice.getValue())
        );

        Either<ErrorMessage, AddMealToMenu.AddMealToMenuRequest> useCaseRequestEth = validator.validate(request);

        assertTrue(useCaseRequestEth.isLeft());
        ErrorMessage errorMessage = useCaseRequestEth.getLeft();
        assertEquals("Invalid parameters: name must be not blank.", errorMessage.getError());
    }

    @Test
    void validate_WithInvalidDescription_ReturnsErrorMessage() {
        MealName mealName = mealName();
        String invalidDescription = "";
        MealPrice mealPrice = mealPrice();
        AddMealToMenuEndpoint.Request request =new AddMealToMenuEndpoint.Request(
                new MealModel(mealName.getValue(), invalidDescription, mealPrice.getValue())
        );

        Either<ErrorMessage, AddMealToMenu.AddMealToMenuRequest> useCaseRequestEth = validator.validate(request);

        assertTrue(useCaseRequestEth.isLeft());
        ErrorMessage errorMessage = useCaseRequestEth.getLeft();
        assertEquals("Invalid parameters: description must be not blank.", errorMessage.getError());
    }

    @Test
    void validate_WithInvalidPrice_ReturnsErrorMessage() {
        MealName mealName = mealName();
        MealDescription mealDescription = mealDescription();
        BigDecimal invalidPrice = new BigDecimal(-10);
        AddMealToMenuEndpoint.Request request =new AddMealToMenuEndpoint.Request(
                new MealModel(mealName.getValue(), mealDescription.getValue(), invalidPrice)
        );

        Either<ErrorMessage, AddMealToMenu.AddMealToMenuRequest> useCaseRequestEth = validator.validate(request);

        assertTrue(useCaseRequestEth.isLeft());
        ErrorMessage errorMessage = useCaseRequestEth.getLeft();
        assertEquals("Invalid parameters: price must be positive.", errorMessage.getError());
    }

    @Test
    void validate_WithSeveralInvalidValues_ReturnsErrorMessage() {
        String invalidName = "";
        String invalidDescription = "";
        MealPrice mealPrice = mealPrice();
        AddMealToMenuEndpoint.Request request =new AddMealToMenuEndpoint.Request(
                new MealModel(invalidName, invalidDescription, mealPrice.getValue())
        );

        Either<ErrorMessage, AddMealToMenu.AddMealToMenuRequest> useCaseRequestEth = validator.validate(request);

        assertTrue(useCaseRequestEth.isLeft());
        ErrorMessage errorMessage = useCaseRequestEth.getLeft();
        assertEquals("Invalid parameters: name must be not blank, description must be not blank.", errorMessage.getError());
    }

    @Test
    void validate_WithoutValidValues_ReturnsErrorMessage() {
        String invalidName = "";
        String invalidDescription = "";
        BigDecimal invalidPrice = new BigDecimal(-10);
        AddMealToMenuEndpoint.Request request =new AddMealToMenuEndpoint.Request(
                new MealModel(invalidName, invalidDescription, invalidPrice)
        );

        Either<ErrorMessage, AddMealToMenu.AddMealToMenuRequest> useCaseRequestEth = validator.validate(request);

        assertTrue(useCaseRequestEth.isLeft());
        ErrorMessage errorMessage = useCaseRequestEth.getLeft();
        assertEquals("Invalid parameters: name must be not blank, description must be not blank, price must be positive.", errorMessage.getError());
    }
}