package com.alex788.restaurant.menu.rest.endpoint.get_all_menu;

import com.alex788.restaurant.menu.rest.EndpointUrl;
import com.alex788.restaurant.menu.rest.model.MealModel;
import com.alex788.restaurant.menu.usecase.GetAllMenu;
import com.alex788.restaurant.menu.usecase.dto.MealInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class GetAllMenuEndpoint {

    private final GetAllMenu getAllMenu;

    @GetMapping(EndpointUrl.API_V1_MENU_GET_ALL)
    public ResponseEntity<Response> execute() {
        List<MealInfo> mealInfos = getAllMenu.execute();
        List<MealModel> mealModels = mealInfos.stream()
                .map(MealModel::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new Response(mealModels));
    }

    @Getter
    @JsonSerialize
    @AllArgsConstructor
    public static class Response {

        @JsonProperty
        private final List<MealModel> mealModels;
    }
}
