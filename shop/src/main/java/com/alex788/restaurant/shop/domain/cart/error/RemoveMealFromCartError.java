package com.alex788.restaurant.shop.domain.cart.error;

import com.alex788.restaurant.common.error.DomainError;

public interface RemoveMealFromCartError extends DomainError {

    class CartDoesNotContainsThisMeal implements RemoveMealFromCartError {
    }
}
