package com.alex788.restaurant.menu.usecase;

import com.alex788.restaurant.menu.usecase.dto.MealInfo;

import java.util.List;

public interface GetAllMenu {

    List<MealInfo> execute();
}
