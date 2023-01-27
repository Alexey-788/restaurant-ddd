package com.alex788.restaurant.menu.postgres_persistence;

import com.alex788.restaurant.menu.domain.Meal;
import com.alex788.restaurant.menu.domain.value_object.MealId;
import com.alex788.restaurant.menu.domain.value_object.MealName;
import com.alex788.restaurant.menu.usecase.access.MealExtracter;
import com.alex788.restaurant.menu.usecase.access.MealPersister;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PostgresMealRepository implements MealPersister, MealExtracter {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final MealResultSetExtractor mealResultSetExtractor = new MealResultSetExtractor();
    private final MealRowMapper mealRowMapper = new MealRowMapper();

    public PostgresMealRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Meal meal) {
        Map<String, ?> params = Map.of("id", meal.getId().getValue());
        Boolean isPersisted = jdbcTemplate.queryForObject("SELECT EXISTS(SELECT * FROM meal.meal WHERE id = :id);", params, Boolean.class);
        if (isPersisted == null) throw new RuntimeException("Pre-persisted check returned null.");

        try {
            if (isPersisted) {
                update(meal);
            } else {
                insert(meal);
            }
        } catch (DuplicateKeyException e) {
            throw new StorageConflictException(e);
        }
    }

    private void insert(Meal meal) {
        Map<String, ?> params = Map.of(
                "id", meal.getId().getValue(),
                "name", meal.getName().getValue(),
                "description", meal.getDescription().getValue(),
                "price", meal.getPrice().getValue()
        );

        // TODO: Fix 'lost update' bug
        jdbcTemplate.update(
                "INSERT INTO meal.meal (id, name, description, price) VALUES (:id, :name, :description, :price);",
                params
        );
    }

    private void update(Meal meal) {
        Map<String, ?> params = Map.of(
                "id", meal.getId().getValue(),
                "name", meal.getName().getValue(),
                "description", meal.getDescription().getValue(),
                "price", meal.getPrice().getValue()
        );

        jdbcTemplate.update(
                "UPDATE meal.meal SET " +
                        "name = :name, " +
                        "description = :description, " +
                        "price = :price " +
                        "WHERE id = :id",
                params
        );
    }

    @Override
    public Optional<Meal> getByName(MealName name) {
        Map<String, ?> params = Map.of("name", name.getValue());
        return jdbcTemplate.query("SELECT * FROM meal.meal WHERE name = :name;", params, mealResultSetExtractor);
    }

    @Override
    public Optional<Meal> getById(MealId id) {
        Map<String, ?> params = Map.of("id", id.getValue());
        return jdbcTemplate.query("SELECT * FROM meal.meal WHERE id = :id;", params, mealResultSetExtractor);
    }

    @Override
    public List<Meal> getAll() {
        return jdbcTemplate.query("SELECT * FROM meal.meal;", mealRowMapper);
    }
}
