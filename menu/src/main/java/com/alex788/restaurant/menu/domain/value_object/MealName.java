package com.alex788.restaurant.menu.domain.value_object;

import com.alex788.restaurant.menu.domain.value_object.error.MealNameError;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MealName {

    private final String value;

    public static Either<MealNameError, MealName> from(String name) {
        if (name.isBlank()) {
            return Either.left(new MealNameError.BlankError());
        }

        return Either.right(new MealName(name));
    }
}
