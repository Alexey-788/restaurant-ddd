package com.alex788.restaurant.shop.domain.cart.value_object;

import com.alex788.restaurant.shop.domain.cart.value_object.error.CustomerIdError;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerId {

    private final String value;

    public static Either<CustomerIdError, CustomerId> from(String customerId) {
        if (containsWhitespace(customerId)) {
            return Either.left(new CustomerIdError.ContainsWhitespace());
        }

        return Either.right(new CustomerId(customerId));
    }

    private static boolean containsWhitespace(String value) {
        return !value.replaceFirst("\\s", "").equals(value);
    }
}
