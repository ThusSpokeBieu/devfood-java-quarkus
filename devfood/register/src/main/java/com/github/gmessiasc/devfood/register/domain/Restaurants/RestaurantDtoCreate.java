package com.github.gmessiasc.devfood.register.domain.Restaurants;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;

import com.github.gmessiasc.devfood.register.domain.Localizations.LocalizationDto;

import io.smallrye.common.constraint.NotNull;

public class RestaurantDtoCreate {

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 20)
    public String owner;

    @NotNull
    @NotEmpty
    @CNPJ
    public String cnpj;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 20)
    public String name; 

    public LocalizationDto localization;
}
