package com.alex788.restaurant.menu.domain.value_object;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.alex788.restaurant.common.Fixtures.faker;

public class Fixtures {

    public static MealId mealId() {
        long value = faker.number().randomNumber();
        return new MealId(value);
    }

    public static MealName mealName() {
        String value = faker.food().dish();
        return MealName.from(value).get();
    }

    public static MealDescription mealDescription() {
        String value = faker.lorem().sentence();
        return MealDescription.from(value).get();
    }

    public static MealPrice mealPrice() {
        String valueInString = faker.number().digits(5);
        BigDecimal value = new BigDecimal(valueInString).divide(new BigDecimal(100), MealPrice.SCALE, RoundingMode.UNNECESSARY);
        return MealPrice.from(value).get();
    }
}
