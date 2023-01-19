package com.alex788.restaurant.menu.access;

import com.alex788.restaurant.menu.domain.Meal;

public interface MealPersister {

    void persist(Meal meal);
}
