package com.alex788.restaurant.shop.domain.menu.invariant;

import com.alex788.restaurant.shop.domain.menu.value_object.MealName;

public interface MealNameIsUnique {

    boolean check(MealName name);
}
