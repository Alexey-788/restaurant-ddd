package com.alex788.restaurant.menu.usecase.scenario;

import com.alex788.restaurant.menu.domain.Meal;
import com.alex788.restaurant.menu.usecase.GetAllMenu;
import com.alex788.restaurant.menu.usecase.access.MealExtracter;
import com.alex788.restaurant.menu.usecase.dto.MealInfo;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GetAllMenuUseCase implements GetAllMenu {

    private final MealExtracter extracter;

    @Override
    public List<MealInfo> execute() {
        List<Meal> meals = extracter.getAll();
        return meals.stream()
                .map(MealInfo::from)
                .collect(Collectors.toList());
    }
}
