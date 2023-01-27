package com.alex788.restaurant.menu.usecase.access;

import com.alex788.restaurant.menu.domain.Meal;

public interface MealPersister {

    void save(Meal meal);
}
