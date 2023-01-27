package com.alex788.restaurant.menu.usecase.dto;

import com.alex788.restaurant.menu.domain.Meal;
import com.alex788.restaurant.menu.domain.value_object.MealDescription;
import com.alex788.restaurant.menu.domain.value_object.MealId;
import com.alex788.restaurant.menu.domain.value_object.MealName;
import com.alex788.restaurant.menu.domain.value_object.MealPrice;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MealInfo {

    private final MealId id;
    private final MealName name;
    private final MealDescription description;
    private final MealPrice price;

    public static MealInfo from(Meal meal) {
        return new MealInfo(
                meal.getId(),
                meal.getName(),
                meal.getDescription(),
                meal.getPrice()
        );
    }
}
