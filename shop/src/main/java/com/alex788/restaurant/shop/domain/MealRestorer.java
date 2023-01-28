package com.alex788.restaurant.shop.domain;

import com.alex788.restaurant.shop.domain.value_object.MealDescription;
import com.alex788.restaurant.shop.domain.value_object.MealId;
import com.alex788.restaurant.shop.domain.value_object.MealName;
import com.alex788.restaurant.shop.domain.value_object.MealPrice;

public class MealRestorer {

    public Meal restore(
            MealId id,
            MealName name,
            MealDescription description,
            MealPrice price
    ) {
        return new Meal(id, name, description, price);
    }
}
