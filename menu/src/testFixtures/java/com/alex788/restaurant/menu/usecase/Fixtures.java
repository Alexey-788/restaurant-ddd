package com.alex788.restaurant.menu.usecase;

import com.alex788.restaurant.menu.domain.Meal;
import com.alex788.restaurant.menu.usecase.access.MealPersister;

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
