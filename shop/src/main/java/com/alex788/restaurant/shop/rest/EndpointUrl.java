package com.alex788.restaurant.shop.rest;

public class EndpointUrl {

    public static final String API_V1 = "/rest/v1";
    public static final String API_V1_MENU = API_V1+"/menu";

    public static final String API_V1_MENU_ADD = API_V1_MENU+"/add";
    public static final String API_V1_MENU_GET_BY_ID = API_V1_MENU+"/get/{id}";
    public static final String API_V1_MENU_GET_ALL = API_V1_MENU;
}
