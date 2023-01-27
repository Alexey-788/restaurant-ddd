package com.alex788.restaurant.menu.rest.model;

import com.alex788.restaurant.menu.usecase.dto.MealInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@JsonSerialize
@AllArgsConstructor
public class MealModel {

    @JsonProperty
    private final String name;
    @JsonProperty
    private final String description;
    @JsonProperty
    private final BigDecimal price;

    public static MealModel from(MealInfo mealInfo) {
        return new MealModel(
                mealInfo.getName().getValue(),
                mealInfo.getDescription().getValue(),
                mealInfo.getPrice().getValue()
        );
    }
}
