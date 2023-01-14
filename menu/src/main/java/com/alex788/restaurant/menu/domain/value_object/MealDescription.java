package com.alex788.restaurant.menu.domain.value_object;

import com.alex788.restaurant.common.error.BusinessError;
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

    public static Either<Error, MealDescription> from(String description) {
        if (description.isBlank()) {
            return Either.left(new BlankError());
        }

        return Either.right(new MealDescription(description));
    }

    public interface Error extends BusinessError {
    }

    public static class BlankError implements Error {
    }
}
