package com.alex788.restaurant.shop.usecase;

import com.alex788.restaurant.shop.domain.menu.Meal;
import com.alex788.restaurant.shop.usecase.access.MealPersister;

import java.util.ArrayList;
import java.util.List;

public class Fixtures {

    public static class TestMealPersister implements MealPersister {

        public List<Meal> storage = new ArrayList<>();

        @Override
        public void save(Meal meal) {
            storage.add(meal);
        }
    }
}
