package com.alex788.restaurant.shop.domain.cart;

import com.alex788.restaurant.shop.domain.cart.value_object.CartId;
import com.alex788.restaurant.shop.domain.cart.value_object.CustomerId;
import com.alex788.restaurant.shop.domain.menu.Meal;

import static com.alex788.restaurant.common.Fixtures.faker;

public class Fixtures {

    public static CartId.Generator idGeneratorThatGenerate(CartId id) {
        return () -> id;
    }

    public static CartId cartId() {
        return new CartId(faker.number().randomNumber());
    }

    public static CustomerId customerId() {
        return CustomerId.from(faker.name().firstName()).get();
    }

    public static Cart emptyCart() {
        CartId.Generator idGenerator = idGeneratorThatGenerate(cartId());
        CustomerId customerId = customerId();
        return Cart.create(idGenerator, customerId);
    }

    public static Cart cartWithOneMeal(Meal meal) {
        Cart cart = emptyCart();
        cart.addMeal(meal);
        return cart;
    }
}
