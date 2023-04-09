package com.github.gmessiasc.devfood.register.domain.Restaurants;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.github.gmessiasc.devfood.register.domain.Restaurants.*;

@Mapper(componentModel = "cdi")
public interface RestaurantMapper {    

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "localization.id", ignore = true)
    public Restaurant toRestaurant(RestaurantDtoCreate dto);
    
    @Mapping(target = "creationDate", dateFormat = "dd/MM/yyyy HH:mm:ss")
    public RestaurantDto toDto(Restaurant restaurant);

    
    public void update(RestaurantDtoCreate dto, @MappingTarget Restaurant restaurant);
    
}
