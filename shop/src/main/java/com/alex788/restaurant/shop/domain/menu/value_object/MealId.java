package com.alex788.restaurant.shop.domain.menu.value_object;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class MealId {

    private final long value;

    public interface MealIdGenerator {

        MealId generate();
    }
}
