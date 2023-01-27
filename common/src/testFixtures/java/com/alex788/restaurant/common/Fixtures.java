package com.alex788.restaurant.common;

import com.github.javafaker.Faker;

public class Fixtures {

    public static final Faker faker = new Faker();

    public static Faker getFaker() {
        return faker;
    }
}
