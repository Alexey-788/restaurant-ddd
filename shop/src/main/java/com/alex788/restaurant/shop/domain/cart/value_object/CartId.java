package com.alex788.restaurant.shop.domain.cart.value_object;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class CartId {

    private final long value;

    public interface Generator {

        CartId generate();
    }
}
