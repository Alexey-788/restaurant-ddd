package com.alex788.restaurant.shop.domain.cart.error;

import com.alex788.restaurant.common.error.DomainError;

public interface AddMealToCartError extends DomainError {

    class MaxCountReachedError implements AddMealToCartError {
    }
}
