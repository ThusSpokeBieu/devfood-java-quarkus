package com.github.gmessiasc.devfood.register.domain.mappers;

import org.mapstruct.Mapper;

import com.github.gmessiasc.devfood.register.domain.dto.RestaurantDto;
import com.github.gmessiasc.devfood.register.domain.entities.Restaurant;

@Mapper(componentModel = "cdi")
public interface RestaurantMapper {    
    
    public Restaurant toRestaurant(RestaurantDto dto);
    
    public RestaurantDto toDto(Restaurant restaurant);
}
