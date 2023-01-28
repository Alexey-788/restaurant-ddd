package com.alex788.restaurant.shop.postgres_persistence;

import com.alex788.restaurant.shop.domain.value_object.MealId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = TestConfiguration.class)
class PostgresMealIdGeneratorTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    DataSource dataSource;
    PostgresMealIdGenerator postgresMealIdGenerator;

    @BeforeEach
    void beforeEach() {
        postgresMealIdGenerator = new PostgresMealIdGenerator(dataSource);
    }

    @Test
    void generate_InvokingTwoTimes_GeneratesIdSequentially() {
        MealId id1 = postgresMealIdGenerator.generate();
        MealId id2 = postgresMealIdGenerator.generate();

        assertEquals(1, id1.getValue());
        assertEquals(2, id2.getValue());
    }
}