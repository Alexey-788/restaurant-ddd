package com.alex788.restaurant.menu.usecase;

import com.alex788.restaurant.menu.access.MealPersister;
import com.alex788.restaurant.menu.domain.Meal;

import java.util.ArrayList;
import java.util.List;

public class Fixtures {

    public static class TestMealPersister implements MealPersister {

        public List<Meal> storage = new ArrayList<>();

        @Override
        public void persist(Meal meal) {
            storage.add(meal);
        }
    }
}
