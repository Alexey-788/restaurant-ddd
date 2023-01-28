package com.alex788.restaurant.shop.rest.endpoint;

import com.alex788.restaurant.shop.domain.menu.value_object.MealDescription;
import com.alex788.restaurant.shop.domain.menu.value_object.MealId;
import com.alex788.restaurant.shop.domain.menu.value_object.MealName;
import com.alex788.restaurant.shop.domain.menu.value_object.MealPrice;
import com.alex788.restaurant.shop.rest.EndpointUrl;
import com.alex788.restaurant.shop.rest.endpoint.add_meal_to_menu.AddMealToMenuEndpoint;
import com.alex788.restaurant.shop.rest.model.MealModel;
import com.alex788.restaurant.shop.usecase.AddMealToMenu;
import com.alex788.restaurant.shop.usecase.error.AddMealToMenuUseCaseError;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static com.alex788.restaurant.shop.domain.Fixtures.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = AddMealToMenuEndpointTest.TestConfiguration.class)
class AddMealToMenuEndpointTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    MockMvc mvc;

    @Autowired
    AddMealToMenu addMealToMenu;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void execute_WithValidValues_ReturnsMealId() throws Exception {
        MealName mealName = mealName();
        MealDescription mealDescription = mealDescription();
        MealPrice mealPrice = mealPrice();

        AddMealToMenuEndpoint.Request request = new AddMealToMenuEndpoint.Request(
                new MealModel(mealName.getValue(), mealDescription.getValue(), mealPrice.getValue())
        );

        MealId mealId = mealId();
        doReturn(Either.right(mealId))
                .when(addMealToMenu)
                .execute(eq(new AddMealToMenu.AddMealToMenuRequest(mealName, mealDescription, mealPrice)));

        AddMealToMenuEndpoint.Response expectedResponse = new AddMealToMenuEndpoint.Response(mealId.getValue());

        mvc.perform(
                post(EndpointUrl.API_V1_MENU_ADD)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                content().contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                content().json(objectMapper.writeValueAsString(expectedResponse))
        ).andExpect(
                status().isOk()
        ).andDo(
                print()
        );
    }

    @Test
    void execute_UseCaseError_ReturnsErrorMessage() throws Exception {
        MealName mealName = mealName();
        MealDescription mealDescription = mealDescription();
        MealPrice mealPrice = mealPrice();

        AddMealToMenuEndpoint.Request request = new AddMealToMenuEndpoint.Request(
                new MealModel(mealName.getValue(), mealDescription.getValue(), mealPrice.getValue())
        );

        doReturn(Either.left(new AddMealToMenuUseCaseError.NameMustBeUniqueError()))
                .when(addMealToMenu)
                .execute(eq(new AddMealToMenu.AddMealToMenuRequest(mealName, mealDescription, mealPrice)));

        mvc.perform(
                post(EndpointUrl.API_V1_MENU_ADD)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                content().contentType(MediaType.APPLICATION_PROBLEM_JSON)
        ).andExpect(
                jsonPath("$.error").value("Meal with same name already exists.")
        ).andExpect(
                status().isBadRequest()
        ).andDo(
                print()
        );
    }

    @Test
    void execute_ValidationError_ReturnsErrorMessage() throws Exception {
        MealPrice mealPrice = mealPrice();

        AddMealToMenuEndpoint.Request request = new AddMealToMenuEndpoint.Request(
                new MealModel("", "", mealPrice.getValue())
        );

        verify(addMealToMenu, never()).execute(any());

        mvc.perform(
                post(EndpointUrl.API_V1_MENU_ADD)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                content().contentType(MediaType.APPLICATION_PROBLEM_JSON)
        ).andExpect(
                jsonPath("$.error").isString()
        ).andExpect(
                status().isBadRequest()
        ).andDo(
                print()
        );
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public AddMealToMenu addMealToMenu() {
            return Mockito.mock(AddMealToMenu.class);
        }

        @Bean
        public AddMealToMenuEndpoint addMealToMenuEndpoint(AddMealToMenu addMealToMenu) {
            return new AddMealToMenuEndpoint(addMealToMenu);
        }
    }
}