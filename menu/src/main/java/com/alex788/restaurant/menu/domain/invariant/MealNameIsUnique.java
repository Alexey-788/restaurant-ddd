package com.alex788.restaurant.menu.domain.invariant;

import com.alex788.restaurant.menu.domain.value_object.MealName;

public interface MealNameIsUnique {

    boolean check(MealName name);
}
