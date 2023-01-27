--liquibase formatted sql

--changeset create-meal-sequence:1
CREATE SEQUENCE IF NOT EXISTS meal.meal_id_seq;

--changeset create-meal-table:1
CREATE TABLE IF NOT EXISTS meal.meal(
    id BIGINT PRIMARY KEY NOT NULL,
    name TEXT NOT NULL UNIQUE,
    description TEXT NOT NULL,
    price NUMERIC NOT NULL
);