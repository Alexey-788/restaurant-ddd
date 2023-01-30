package com.alex788.restaurant.shop.domain.cart.value_object;

import com.alex788.restaurant.shop.domain.cart.value_object.error.CustomerIdError;
import io.vavr.control.Either;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CustomerIdTest {

    @ParameterizedTest
    @ValueSource(strings = {"Some-Id", "SomeId"})
    void from_StringWithoutWhitespaces_ReturnsCustomerId(String value) {
        Either<CustomerIdError, CustomerId> customerIdEth = CustomerId.from(value);

        assertTrue(customerIdEth.isRight());
        CustomerId customerId = customerIdEth.get();
        assertEquals(value, customerId.getValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Some Id", "Some\tId", "Some\nId", "Some  Id"})
    void from_StringWithWhitespace_ReturnsError(String value) {
        Either<CustomerIdError, CustomerId> customerIdEth = CustomerId.from(value);

        assertTrue(customerIdEth.isLeft());
        assertInstanceOf(CustomerIdError.ContainsWhitespace.class, customerIdEth.getLeft());
    }
}