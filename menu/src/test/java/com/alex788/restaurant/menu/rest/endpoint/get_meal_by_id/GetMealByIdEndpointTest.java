package com.alex788.restaurant.menu.rest.endpoint.get_meal_by_id;

import com.alex788.restaurant.menu.domain.Meal;
import com.alex788.restaurant.menu.rest.EndpointUrl;
import com.alex788.restaurant.menu.rest.model.MealModel;
import com.alex788.restaurant.menu.usecase.GetMealById;
import com.alex788.restaurant.menu.usecase.dto.MealInfo;
import com.alex788.restaurant.menu.usecase.error.GetMealByIdUseCaseError;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static com.alex788.restaurant.menu.domain.Fixtures.newMeal;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = GetMealByIdEndpointTest.TestConfiguration.class)
class GetMealByIdEndpointTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    MockMvc mvc;
    @Autowired
    GetMealById getMealById;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void execute_MealExists_ReturnsMeal() throws Exception {
        Meal meal = newMeal();
        doReturn(Either.right(MealInfo.from(meal))).when(getMealById).execute(meal.getId());

        String url = urlForMealWithId(meal.getId().getValue());

        GetMealByIdEndpoint.Response expectedResponse = new GetMealByIdEndpoint.Response(new MealModel(
                meal.getName().getValue(),
                meal.getDescription().getValue(),
                meal.getPrice().getValue()
        ));

        mvc.perform(
                get(url).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().json(objectMapper.writeValueAsString(expectedResponse))
        ).andDo(print());
    }

    @Test
    void execute_MealNotExists_ReturnsMeal() throws Exception {
        Meal meal = newMeal();
        doReturn(Either.left(new GetMealByIdUseCaseError.NotFoundError())).when(getMealById).execute(meal.getId());

        String url = urlForMealWithId(meal.getId().getValue());

        mvc.perform(
                get(url).contentType(MediaType.APPLICATION_PROBLEM_JSON)
        ).andExpect(
                status().isNotFound()
        ).andExpect(
                jsonPath("$.error").value("No meal found with provided id.")
        ).andDo(print());
    }

    String urlForMealWithId(long id) {
        return EndpointUrl.API_V1_MENU_GET_BY_ID.replace("{id}", id + "");
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public GetMealById getMealById() {
            return mock(GetMealById.class);
        }

        @Bean
        public GetMealByIdEndpoint getMealByIdEndpoint(GetMealById getMealById) {
            return new GetMealByIdEndpoint(getMealById);
        }
    }
}