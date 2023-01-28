package com.alex788.restaurant.shop.usecase.access;

import com.alex788.restaurant.shop.domain.menu.Meal;

public interface MealPersister {

    void save(Meal meal);
}
