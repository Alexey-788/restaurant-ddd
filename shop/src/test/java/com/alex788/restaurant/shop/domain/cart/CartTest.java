package com.alex788.restaurant.shop.domain.cart;

import com.alex788.restaurant.common.type.Count;
import com.alex788.restaurant.shop.domain.cart.error.AddMealToCartError;
import com.alex788.restaurant.shop.domain.cart.error.RemoveMealFromCartError;
import com.alex788.restaurant.shop.domain.cart.value_object.CartId;
import com.alex788.restaurant.shop.domain.cart.value_object.CustomerId;
import com.alex788.restaurant.shop.domain.menu.Meal;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.alex788.restaurant.common.type.Fixtures.count;
import static com.alex788.restaurant.common.type.Fixtures.maxCount;
import static com.alex788.restaurant.shop.domain.cart.Fixtures.*;
import static com.alex788.restaurant.shop.domain.menu.Fixtures.newMeal;
import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    @Test
    void create_ReturnsNewCart() {
        CartId id = cartId();
        CustomerId customerId = customerId();
        Cart cart = Cart.create(idGeneratorThatGenerate(id), customerId);

        assertNotNull(cart);
        assertEquals(id, cart.getId());
        assertEquals(customerId, cart.getCustomerId());
        assertTrue(cart.getMeals().isEmpty());
    }

    @Test
    void addMeal_WithNotStoredMeal_AddsMealIdToCartWithOneCount() {
        Meal meal = newMeal();
        Cart cart = emptyCart();

        Optional<AddMealToCartError> errorOpt = cart.addMeal(meal);

        assertTrue(errorOpt.isEmpty());
        assertTrue(cart.getMeals().containsKey(meal.getId()));
        assertEquals(Count.one(), cart.getMeals().get(meal.getId()));
    }

    @Test
    void addMeal_WithStoredMeal_IncreasesMealCount() {
        Meal meal = newMeal();
        Cart cart = cartWithOneMeal(meal);

        assertEquals(count(1), cart.getMeals().get(meal.getId()));

        Optional<AddMealToCartError> errorOpt = cart.addMeal(meal);

        assertTrue(errorOpt.isEmpty());
        assertEquals(count(2), cart.getMeals().get(meal.getId()));
    }

    @Test
    void addMeal_WithMaxStoredMeal_ReturnsError() {
        Meal meal = newMeal();
        Cart cart = emptyCart();
        cart.getMeals().put(meal.getId(), maxCount());

        Optional<AddMealToCartError> errorOpt = cart.addMeal(meal);

        assertTrue(errorOpt.isPresent());
        assertInstanceOf(AddMealToCartError.MaxCountReachedError.class , errorOpt.get());
    }

    @Test
    void removeMeal_CartContainsOneOfMeal_RemovesMeal() {
        Meal meal = newMeal();
        Cart cart = cartWithOneMeal(meal);

        assertTrue(cart.getMeals().containsKey(meal.getId()));

        Optional<RemoveMealFromCartError> errorOpt = cart.removeMeal(meal);

        assertTrue(errorOpt.isEmpty());
        assertFalse(cart.getMeals().containsKey(meal.getId()));
    }

    @Test
    void removeMeal_CartContainsManyOfThisMeal_RemovesAllOfTheseMeals() {
        Meal meal = newMeal();
        Cart cart = emptyCart();
        cart.getMeals().put(meal.getId(), count(100));

        assertTrue(cart.getMeals().containsKey(meal.getId()));

        Optional<RemoveMealFromCartError> errorOpt = cart.removeMeal(meal);

        assertTrue(errorOpt.isEmpty());
        assertFalse(cart.getMeals().containsKey(meal.getId()));
    }

    @Test
    void removeMeal_CartNotContainsThisMeal_ReturnsError() {
        Meal meal = newMeal();
        Cart cart = emptyCart();

        Optional<RemoveMealFromCartError> errorOpt = cart.removeMeal(meal);

        assertTrue(errorOpt.isPresent());
        RemoveMealFromCartError error = errorOpt.get();
        assertInstanceOf(RemoveMealFromCartError.CartDoesNotContainsThisMeal.class, error);
    }
}