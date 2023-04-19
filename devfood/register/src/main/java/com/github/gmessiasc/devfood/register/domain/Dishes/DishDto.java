package com.github.gmessiasc.devfood.register.domain.Dishes;

import java.math.BigDecimal;

import com.github.gmessiasc.devfood.register.domain.Restaurants.RestaurantDtoCreate;

public class DishDto {
    public String name;

    public String description;

    public RestaurantDtoCreate restaurant;

    public BigDecimal price;
}
