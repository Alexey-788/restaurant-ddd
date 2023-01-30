package com.alex788.restaurant.shop.domain.cart.value_object.error;

import com.alex788.restaurant.common.error.DomainError;

public interface CustomerIdError extends DomainError {

    class ContainsWhitespace implements CustomerIdError {
    }
}
