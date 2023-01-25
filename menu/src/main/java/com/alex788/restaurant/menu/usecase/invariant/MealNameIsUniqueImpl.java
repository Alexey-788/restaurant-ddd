package com.alex788.restaurant.menu.usecase.invariant;

import com.alex788.restaurant.menu.domain.invariant.MealNameIsUnique;
import com.alex788.restaurant.menu.domain.value_object.MealName;
import com.alex788.restaurant.menu.usecase.access.MealExtracter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MealNameIsUniqueImpl implements MealNameIsUnique {

    private final MealExtracter mealExtracter;

    @Override
    public boolean check(MealName name) {
        return mealExtracter.getByName(name).isEmpty();
    }
}
