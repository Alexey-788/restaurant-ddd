package com.alex788.restaurant.shop.postgres_persistence;

import com.alex788.restaurant.shop.domain.menu.value_object.MealId;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class PostgresMealIdGenerator implements MealId.MealIdGenerator {

    private final JdbcTemplate jdbcTemplate;

    public PostgresMealIdGenerator(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public MealId generate() {
        Long id = jdbcTemplate.queryForObject("SELECT nextval('meal.meal_id_seq');", Long.class);
        if (id == null) throw new RuntimeException("Query for id returned null.");
        return new MealId(id);
    }
}
