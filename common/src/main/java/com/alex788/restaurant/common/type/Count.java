package com.alex788.restaurant.common.type;

import com.alex788.restaurant.common.type.error.CountError;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Count {

    private final int value;

    private final static Count ONE = from(1).get();

    public static Either<CountError.NegativeError, Count> from(int count) {
        if (count < 0) {
            return Either.left(new CountError.NegativeError());
        }

        return Either.right(new Count(count));
    }

    public static Count one() {
        return ONE;
    }

    public Either<CountError.MaxReached, Count> increment() {
        int newValue = value+1;

        if (newValue < 0) {
            return Either.left(new CountError.MaxReached());
        }

        return Either.right(new Count(newValue));
    }

    public Either<CountError.MinReached, Count> decrement() {
        int newValue = value-1;

        if (newValue < 0) {
            return Either.left(new CountError.MinReached());
        }

        return Either.right(new Count(newValue));
    }
}
