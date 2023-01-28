package com.alex788.restaurant.shop.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@JsonSerialize
@AllArgsConstructor
public class ErrorMessage {

    @JsonProperty
    private final String error;
}
