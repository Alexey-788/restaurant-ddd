package com.alex788.restaurant.shop.rest.endpoint.add_meal_to_menu;

import com.alex788.restaurant.shop.domain.menu.value_object.MealId;
import com.alex788.restaurant.shop.rest.EndpointUrl;
import com.alex788.restaurant.shop.rest.model.ErrorMessage;
import com.alex788.restaurant.shop.rest.model.MealModel;
import com.alex788.restaurant.shop.usecase.AddMealToMenu;
import com.alex788.restaurant.shop.usecase.error.AddMealToMenuUseCaseError;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AddMealToMenuEndpoint {

    private final AddMealToMenu addMealToMenu;
    private final AddMealToMenuRequestValidator addMealToMenuRequestValidator = new AddMealToMenuRequestValidator();
    private final AddMealToMenuErrorMessageMapper addMealToMenuErrorMessageMapper = new AddMealToMenuErrorMessageMapper();

    @PostMapping(EndpointUrl.API_V1_MENU_ADD)
    public ResponseEntity<?> execute(@RequestBody Request request) {
        Either<ErrorMessage, AddMealToMenu.AddMealToMenuRequest> useCaseRequestEth = addMealToMenuRequestValidator.validate(request);

        if (useCaseRequestEth.isLeft()) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                    .body(useCaseRequestEth.getLeft());
        }

        Either<AddMealToMenuUseCaseError, MealId> mealIdEth = addMealToMenu.execute(useCaseRequestEth.get());

        if (mealIdEth.isLeft()) {
            AddMealToMenuUseCaseError error = mealIdEth.getLeft();
            String errorMessage = error.access(addMealToMenuErrorMessageMapper);

            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                    .body(new ErrorMessage(errorMessage));
        }

        long id = mealIdEth.get().getValue();
        return ResponseEntity.ok(new Response(id));
    }

    @Getter
    @JsonSerialize
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        @JsonProperty
        private MealModel mealModel;
    }

    @Getter
    @JsonSerialize
    @AllArgsConstructor
    public static class Response {

        @JsonProperty
        private final long id;
    }
}
