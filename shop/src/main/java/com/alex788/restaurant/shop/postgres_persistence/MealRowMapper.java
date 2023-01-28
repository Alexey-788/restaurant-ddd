package com.alex788.restaurant.shop.postgres_persistence;

import com.alex788.restaurant.shop.domain.menu.Meal;
import com.alex788.restaurant.shop.domain.menu.MealRestorer;
import com.alex788.restaurant.shop.domain.menu.value_object.MealDescription;
import com.alex788.restaurant.shop.domain.menu.value_object.MealId;
import com.alex788.restaurant.shop.domain.menu.value_object.MealName;
import com.alex788.restaurant.shop.domain.menu.value_object.MealPrice;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MealRowMapper implements RowMapper<Meal> {

    private final MealRestorer mealRestorer = new MealRestorer();

    @Override
    public Meal mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        BigDecimal price = rs.getBigDecimal("price");

        return restoreMeal(id, name, description, price);
    }

    private Meal restoreMeal(long id, String name, String description, BigDecimal price) {
        MealId mealId = new MealId(id);

        MealName mealName = MealName.from(name)
                .getOrElseThrow((error) -> new RuntimeException("Incorrect meal name '" + name + "' for meal with id " + id + "."));

        MealDescription mealDescription = MealDescription.from(description)
                .getOrElseThrow((error) -> new RuntimeException("Incorrect meal description '" + description + "' for meal with id " + id + "."));

        MealPrice mealPrice = MealPrice.from(price)
                .getOrElseThrow((error) -> new RuntimeException("Incorrect meal price '" + price + "' for meal with id " + id + "."));

        return mealRestorer.restore(mealId, mealName, mealDescription, mealPrice);
    }
}
