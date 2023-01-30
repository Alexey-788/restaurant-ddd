package com.alex788.restaurant.common.type;

import static com.alex788.restaurant.common.Fixtures.faker;

public class Fixtures {

    public static Count count() {
        int zeroOrPositiveNumber = faker.number().numberBetween(0, 1000);
        return Count.from(zeroOrPositiveNumber).get();
    }

    public static Count count(int value) {
        return Count.from(value).get();
    }

    public static Count maxCount() {
        return Count.from(Integer.MAX_VALUE).get();
    }

    public static Count minCount() {
        return Count.from(0).get();
    }
}
