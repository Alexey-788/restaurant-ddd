package com.alex788.restaurant.menu.postgres_persistence;

import com.alex788.restaurant.menu.domain.Meal;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MealResultSetExtractor implements ResultSetExtractor<Optional<Meal>> {

    private final MealRowMapper mealRowMapper = new MealRowMapper();

    @Override
    public Optional<Meal> extractData(ResultSet rs) throws SQLException, DataAccessException {
        if (rs.next()) {
            return Optional.ofNullable(mealRowMapper.mapRow(rs, 0));
        } else {
            return Optional.empty();
        }
    }
}
