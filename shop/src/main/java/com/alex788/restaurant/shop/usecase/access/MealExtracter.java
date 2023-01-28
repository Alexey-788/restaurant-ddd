package com.alex788.restaurant.shop.usecase.access;

import com.alex788.restaurant.shop.domain.Meal;
import com.alex788.restaurant.shop.domain.value_object.MealId;
import com.alex788.restaurant.shop.domain.value_object.MealName;

import java.util.List;
import java.util.Optional;

public interface MealExtracter {

    Optional<Meal> getByName(MealName name);
    Optional<Meal> getById(MealId id);
    List<Meal> getAll();

}
