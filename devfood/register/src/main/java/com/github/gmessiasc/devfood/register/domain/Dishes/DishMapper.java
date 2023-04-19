package com.github.gmessiasc.devfood.register.domain.Dishes;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.github.gmessiasc.devfood.register.domain.Dishes.DishDto;
import com.github.gmessiasc.devfood.register.domain.Dishes.DishUpdateDto;
import com.github.gmessiasc.devfood.register.domain.Dishes.Dish;

@Mapper(componentModel = "cdi")
public interface DishMapper {
    
    public Dish toDish(DishDto dto);
    
    public DishDto toDto(Dish dish);

    public void updateDishFromDto(DishUpdateDto dto, @MappingTarget Dish dish);

}
