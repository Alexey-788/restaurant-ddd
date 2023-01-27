package com.alex788.restaurant.menu.rest.endpoint.get_meal_by_id;

import com.alex788.restaurant.menu.domain.value_object.MealId;
import com.alex788.restaurant.menu.rest.EndpointUrl;
import com.alex788.restaurant.menu.rest.model.ErrorMessage;
import com.alex788.restaurant.menu.rest.model.MealModel;
import com.alex788.restaurant.menu.usecase.GetMealById;
import com.alex788.restaurant.menu.usecase.dto.MealInfo;
import com.alex788.restaurant.menu.usecase.error.GetMealByIdUseCaseError;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class GetMealByIdEndpoint {

    private final GetMealById getMealById;
    private final GetMealByIdErrorMessageMapper getMealByIdErrorMessageMapper = new GetMealByIdErrorMessageMapper();

    @GetMapping(EndpointUrl.API_V1_MENU_GET_BY_ID)
    public ResponseEntity<?> execute(@PathVariable long id) {
        Either<GetMealByIdUseCaseError, MealInfo> mealInfoEth = getMealById.execute(new MealId(id));

        if (mealInfoEth.isLeft()) {
            GetMealByIdUseCaseError error = mealInfoEth.getLeft();
            ErrorMessage errorMessage = new ErrorMessage(error.accept(getMealByIdErrorMessageMapper));
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }

        MealInfo mealInfo = mealInfoEth.get();
        Response response = new Response(MealModel.from(mealInfo));

        return ResponseEntity.ok(response);
    }

    @Getter
    @JsonSerialize
    @AllArgsConstructor
    public static class Request {

        @JsonProperty
        private final long id;
    }

    @Getter
    @JsonSerialize
    @AllArgsConstructor
    public static class Response {

        @JsonProperty
        private final MealModel mealModel;
    }
}
