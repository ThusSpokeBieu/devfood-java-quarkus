package com.github.gmessiasc.devfood.register.domain.mappers;

import org.mapstruct.Mapper;

import com.github.gmessiasc.devfood.register.domain.dto.DishDto;
import com.github.gmessiasc.devfood.register.domain.entities.Dish;

@Mapper(componentModel = "cdi")
public interface DishMapper {
    
    public Dish toDish(DishDto dto);
    
    public DishDto toDto(Dish dish);
}
