package com.alex788.restaurant.common.type;

import com.alex788.restaurant.common.type.error.CountError;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.alex788.restaurant.common.type.Fixtures.count;
import static org.junit.jupiter.api.Assertions.*;

class CountTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 11})
    void from_WithPositiveOrZeroValue_ReturnsCount(int value) {
        Either<CountError.NegativeError, Count> countEth = Count.from(value);

        assertTrue(countEth.isRight());
        Count count = countEth.get();
        assertEquals(value, count.getValue());
    }

    @ParameterizedTest
    @ValueSource(ints = -19)
    void from_WithNegativeValue_ReturnsError(int value) {
        Either<CountError.NegativeError, Count> countEth = Count.from(value);

        assertTrue(countEth.isLeft());
        assertInstanceOf(CountError.NegativeError.class, countEth.getLeft());
    }

    @Test
    void one_ReturnsCountWithOneValue() {
        Count count = Count.one();

        assertEquals(1, count.getValue());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 30})
    void increment_WithValidValue_ReturnsIncrementedCount(int value) {
        Count count = count(value);

        Either<CountError.MaxReached, Count> incrementedCountEth = count.increment();

        assertTrue(incrementedCountEth.isRight());
        Count incrementedCount = incrementedCountEth.get();
        assertEquals(count.getValue()+1, incrementedCount.getValue());
    }

    @Test
    void increment_WithMaxValue_ReturnsError() {
        Count count = count(Integer.MAX_VALUE);

        Either<CountError.MaxReached, Count> incrementedCountEth = count.increment();

        assertTrue(incrementedCountEth.isLeft());
        assertInstanceOf(CountError.MaxReached.class, incrementedCountEth.getLeft());
    }

    @ParameterizedTest
    @ValueSource(ints = {30, Integer.MAX_VALUE})
    void decrement_WithValidValue_ReturnsDecrementedCount(int value) {
        Count count = count(value);

        Either<CountError.MinReached, Count> decrementedCountEth = count.decrement();

        assertTrue(decrementedCountEth.isRight());
        Count incrementedCount = decrementedCountEth.get();
        assertEquals(count.getValue()-1, incrementedCount.getValue());
    }

    @Test
    void decrement_WithZeroValue_ReturnsError() {
        Count count = count(0);

        Either<CountError.MinReached, Count> decrementedCountEth = count.decrement();

        assertTrue(decrementedCountEth.isLeft());
        assertInstanceOf(CountError.MinReached.class, decrementedCountEth.getLeft());
    }
}