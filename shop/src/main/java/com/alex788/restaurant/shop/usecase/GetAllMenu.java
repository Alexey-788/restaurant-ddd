package com.alex788.restaurant.shop.usecase;

import com.alex788.restaurant.shop.usecase.dto.MealInfo;

import java.util.List;

public interface GetAllMenu {

    List<MealInfo> execute();
}
