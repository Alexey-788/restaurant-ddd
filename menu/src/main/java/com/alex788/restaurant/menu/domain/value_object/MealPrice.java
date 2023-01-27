package com.alex788.restaurant.menu.domain.value_object;

import com.alex788.restaurant.menu.domain.value_object.error.MealPriceError;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MealPrice {

    public static final int SCALE = 2;

    private final BigDecimal value;

    public static Either<MealPriceError, MealPrice> from(BigDecimal price) {
        if (price.scale() > SCALE) {
            return Either.left(new MealPriceError.WrongScaleError());
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) { // price < 0
            return Either.left(new MealPriceError.NegativeError());
        }

        return Either.right(new MealPrice(price));
    }
}
