package com.alex788.restaurant.shop.domain.menu.value_object;

import com.alex788.restaurant.shop.domain.menu.value_object.error.MealDescriptionError;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MealDescription {

    private final String value;

    public static Either<MealDescriptionError, MealDescription> from(String description) {
        if (description.isBlank()) {
            return Either.left(new MealDescriptionError.BlankError());
        }

        return Either.right(new MealDescription(description));
    }
}
