package com.alex788.restaurant.shop.domain.cart;

import com.alex788.restaurant.common.type.Count;
import com.alex788.restaurant.common.type.error.CountError;
import com.alex788.restaurant.shop.domain.cart.error.AddMealToCartError;
import com.alex788.restaurant.shop.domain.cart.error.RemoveMealFromCartError;
import com.alex788.restaurant.shop.domain.cart.value_object.CartId;
import com.alex788.restaurant.shop.domain.cart.value_object.CustomerId;
import com.alex788.restaurant.shop.domain.menu.Meal;
import com.alex788.restaurant.shop.domain.menu.value_object.MealId;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter(value = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Cart {

    private final CartId id;
    private final Map<MealId, Count> meals;
    private final CustomerId customerId;
    private final OffsetDateTime created;

    public static Cart create(
            CartId.Generator idGenerator,
            CustomerId customerId
    ) {
        return new Cart(
                idGenerator.generate(),
                new HashMap<>(),
                customerId,
                OffsetDateTime.now()
        );
    }

    public Optional<AddMealToCartError> addMeal(Meal meal) {
        MealId mealId = meal.getId();

        if (meals.get(mealId) == null) {
            addNewMeal(mealId);
        } else {
            return incrementMealCount(mealId)
                    .map((err) -> err);
        }

        return Optional.empty();
    }

    private void addNewMeal(MealId mealId) {
        Count count = Count.one();

        meals.put(mealId, count);
    }

    private Optional<AddMealToCartError.MaxCountReachedError> incrementMealCount(MealId mealId) {
        Count oldCount = meals.get(mealId);
        Either<CountError.MaxReached, Count> newCountEth = oldCount.increment();

        if (newCountEth.isLeft()) {
            return Optional.of(new AddMealToCartError.MaxCountReachedError());
        }

        Count newCount = newCountEth.get();
        meals.put(mealId, newCount);

        return Optional.empty();
    }

    public Optional<RemoveMealFromCartError> removeMeal(Meal meal) {
        MealId mealId = meal.getId();
        if (!meals.containsKey(mealId)) {
            return Optional.of(new RemoveMealFromCartError.CartDoesNotContainsThisMeal());
        }
        meals.remove(mealId);
        return Optional.empty();
    }
}
