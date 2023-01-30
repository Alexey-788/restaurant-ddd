package com.alex788.restaurant.shop.rest.endpoint.get_all_menu;

import com.alex788.restaurant.shop.rest.EndpointUrl;
import com.alex788.restaurant.shop.rest.model.MealModel;
import com.alex788.restaurant.shop.usecase.GetAllMenu;
import com.alex788.restaurant.shop.usecase.dto.MealInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.alex788.restaurant.shop.domain.menu.Fixtures.newMeal;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = GetAllMenuEndpointTest.TestConfiguration.class)
class GetAllMenuEndpointTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    MockMvc mvc;
    @Autowired
    GetAllMenu getAllMenu;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void execute_UseCaseReturnsThreeMeals_ReturnsThreeMeals() throws Exception {
        List<MealInfo> mealInfos = List.of(
                MealInfo.from(newMeal()), MealInfo.from(newMeal()), MealInfo.from(newMeal())
        );
        doReturn(mealInfos).when(getAllMenu).execute();

        GetAllMenuEndpoint.Response expectedResponse = new GetAllMenuEndpoint.Response(
                mealInfos.stream()
                        .map(MealModel::from)
                        .collect(Collectors.toList())
        );

        mvc.perform(
                get(EndpointUrl.API_V1_MENU_GET_ALL)
        ).andExpect(
                content().contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().json(objectMapper.writeValueAsString(expectedResponse))
        ).andDo(MockMvcResultHandlers.print());
    }

    @Test
    void execute_UseCaseReturnsNoMeals_ReturnsEmptyList() throws Exception {
        List<MealInfo> mealInfos = Collections.emptyList();
        doReturn(mealInfos).when(getAllMenu).execute();

        GetAllMenuEndpoint.Response expectedResponse = new GetAllMenuEndpoint.Response(
                Collections.emptyList()
        );

        mvc.perform(
                get(EndpointUrl.API_V1_MENU_GET_ALL)
        ).andExpect(
                content().contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().json(objectMapper.writeValueAsString(expectedResponse))
        ).andDo(MockMvcResultHandlers.print());
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public GetAllMenu getAllMenu() {
            return mock(GetAllMenu.class);
        }

        @Bean
        public GetAllMenuEndpoint getAllMenuEndpoint(GetAllMenu getAllMenu) {
            return new GetAllMenuEndpoint(getAllMenu);
        }
    }
}