package com.alex788.restaurant.shop.domain.invariant;

import com.alex788.restaurant.shop.domain.value_object.MealName;

public interface MealNameIsUnique {

    boolean check(MealName name);
}
