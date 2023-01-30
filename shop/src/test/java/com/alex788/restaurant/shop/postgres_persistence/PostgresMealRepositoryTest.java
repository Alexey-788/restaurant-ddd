package com.alex788.restaurant.shop.postgres_persistence;

import com.alex788.restaurant.shop.domain.menu.Meal;
import com.alex788.restaurant.shop.domain.menu.value_object.MealId;
import com.alex788.restaurant.shop.domain.menu.value_object.MealName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.alex788.restaurant.shop.domain.menu.Fixtures.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = {TestConfiguration.class})
class PostgresMealRepositoryTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    DataSource dataSource;

    PostgresMealRepository repository;

    @BeforeEach
    void beforeEach() {
        repository = new PostgresMealRepository(dataSource);
    }

    @Test
    void persist_WithNewMeal_SavesMeal() {
        Meal meal = newMeal();

        repository.save(meal);

        assertTrue(dbContainsMeal(meal));
    }

    @Test
    void persist_WithMealWithSameId_UpdatesMeal() {
        MealId mealId = mealId();

        Meal firstMeal = newMeal(mealId);
        repository.save(firstMeal);

        Meal secondMeal = newMeal(mealId);
        repository.save(secondMeal);

        assertFalse(dbContainsMeal(firstMeal));
        assertTrue(dbContainsMeal(secondMeal));
    }

    @Test
    void persist_WithMealWithSameName_ThrowsException() {
        MealName mealName = mealName();

        Meal fistMeal = newMeal(mealName);
        repository.save(fistMeal);

        Meal secondMeal = newMeal(mealName);
        assertThrows(StorageConflictException.class, () -> repository.save(secondMeal));
    }

    @Test
    void getByName_WhenMealExists_ReturnsMeal() {
        MealName mealName = mealName();
        Meal persistedMeal = newMeal(mealName);
        repository.save(persistedMeal);

        Optional<Meal> resultMealOpt = repository.getByName(mealName);

        assertTrue(resultMealOpt.isPresent());
        assertTrue(mealsAreEquals(persistedMeal, resultMealOpt.get()));
    }

    @Test
    void getByName_WhenMealNotExists_ReturnsEmptyOpt() {
        MealName mealName = mealName();

        Optional<Meal> resultMealOpt = repository.getByName(mealName);

        assertTrue(resultMealOpt.isEmpty());
    }

    @Test
    void getById_WhenMealExists_ReturnsMeal() {
        MealId mealId = mealId();
        Meal meal = newMeal(mealId);
        repository.save(meal);

        Optional<Meal> resultMealOpt = repository.getById(mealId);

        assertTrue(resultMealOpt.isPresent());
        assertTrue(mealsAreEquals(meal, resultMealOpt.get()));
    }

    @Test
    void getById_WhenMealNotExists_ReturnsEmptyOpt() {
        MealId mealId = mealId();

        Optional<Meal> resultMealOpt = repository.getById(mealId);

        assertTrue(resultMealOpt.isEmpty());
    }

    @Test
    void getAll_ThreeMealsInStorage_ReturnsThreeMeals() {
        cleanMealTable();
        List<Meal> meals = List.of(newMeal(), newMeal(), newMeal());
        meals.forEach(repository::save);

        List<Meal> resultMeals = repository.getAll();

        assertEquals(meals.size(), resultMeals.size());
        for (int i = 0; i < meals.size(); i++) {
            assertTrue(mealsAreEquals(meals.get(i), resultMeals.get(i)));
        }
    }

    @Test
    void getAll_NoMealsInStorage_ReturnsEmptyList() {
        cleanMealTable();
        List<Meal> resultMeals = repository.getAll();

        assertEquals(0, resultMeals.size());
    }

    boolean dbContainsMeal(Meal meal) {
        Map<String, ?> params = Map.of(
                "id", meal.getId().getValue(),
                "name", meal.getName().getValue(),
                "description", meal.getDescription().getValue(),
                "price", meal.getPrice().getValue()
        );

        Boolean exists = jdbcTemplate.queryForObject("SELECT EXISTS(SELECT * FROM meal.meal WHERE " +
                "id = :id AND " +
                "name = :name AND " +
                "description = :description AND " +
                "price = :price " +
                ");", params, Boolean.class);
        assert exists != null;

        return exists;
    }

    boolean mealsAreEquals(Meal meal1, Meal meal2) {
        return meal1.getId().equals(meal2.getId()) &&
                meal1.getName().equals(meal2.getName()) &&
                meal1.getDescription().equals(meal2.getDescription()) &&
                meal1.getPrice().equals(meal2.getPrice());
    }

    void cleanMealTable() {
        jdbcTemplate.update("TRUNCATE TABLE meal.meal;", Map.of());
    }
}