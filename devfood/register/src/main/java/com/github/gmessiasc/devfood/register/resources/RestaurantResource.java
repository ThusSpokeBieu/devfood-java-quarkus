package com.github.gmessiasc.devfood.register.resources;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.github.gmessiasc.devfood.register.domain.Dishes.DishDto;
import com.github.gmessiasc.devfood.register.domain.Dishes.DishUpdateDto;
import com.github.gmessiasc.devfood.register.domain.Restaurants.RestaurantDtoCreate;
import com.github.gmessiasc.devfood.register.domain.Dishes.Dish;
import com.github.gmessiasc.devfood.register.domain.Restaurants.Restaurant;
import com.github.gmessiasc.devfood.register.domain.Restaurants.RestaurantDto;
import com.github.gmessiasc.devfood.register.domain.Dishes.DishMapper;
import com.github.gmessiasc.devfood.register.domain.Restaurants.RestaurantMapper;

@Path("/restaurants")
@Tag(name = "Restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantResource {
    
    @Inject
    RestaurantMapper restaurantMapper;


    @Inject
    DishMapper dishMapper;

    @GET
    public Response getRestaurants() {
        List<RestaurantDto> restaurantDtos = Restaurant.listAll().stream()
                                                .filter(restaurant -> restaurant instanceof Restaurant)
                                                .map(restaurant -> restaurantMapper.toDto((Restaurant) restaurant))
                                                .collect(Collectors.toList());
        return Response.status(Status.OK)
                    .entity(restaurantDtos)
                    .build();
    }

    @GET
    @Path("{id}")
    public Response getRestaurantById(
        @PathParam("id") Long id
        ) {
        Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(id);
            
        if(restaurantOptional.isEmpty()){
            throw new NotFoundException();
        }

        Restaurant restaurant = restaurantOptional.get();
        RestaurantDto dto = restaurantMapper.toDto(restaurant);
        return Response.status(Status.OK)
                    .entity(dto)
                    .build();
    }

    @POST
    @Transactional
    public Response addRestaurant(@Valid RestaurantDtoCreate dto) {
        Restaurant restaurant = restaurantMapper.toRestaurant(dto);
        restaurant.persist();
        return Response.status(Status.CREATED).entity(restaurant).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateRestaurant(
        @PathParam("id") Long id, 
        RestaurantDtoCreate dto) {
            Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(id);
            
            if(restaurantOptional.isEmpty()){
                throw new NotFoundException();
            }

            Restaurant restaurant = restaurantOptional.get();
            restaurantMapper.update(dto, restaurant);
            restaurant.persist();

            return Response.status(Status.OK).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteRestaurant(
        @PathParam("id") Long id, 
        Restaurant dto) {
            Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(id);
            
            restaurantOptional.ifPresentOrElse(Restaurant::delete, () -> {
                throw new NotFoundException("This restaurant doens't exists");
            });

            return Response.status(Status.OK).build();
    }

    @GET
    @Path("{restaurantId}/dish/")
    @Tag(name = "Dishes")
    public Response getDishes(@PathParam("restaurantId") Long restaurantId) {
        Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(restaurantId);

        if (restaurantOptional.isEmpty()) {
            throw new NotFoundException("This restaurant doesn't exists");
        }

        List<Dish> dishes = Dish.list("restaurant", restaurantOptional.get());

        List<DishDto> dishesDto = dishes.stream()
                                    .filter(dish ->  dish instanceof Dish)
                                    .map(dish -> dishMapper.toDto(dish))
                                    .collect(Collectors.toList());

        return Response.status(Status.OK)
                    .entity(dishesDto)
                    .build();
    }

    @POST
    @Path("{restaurantId}/dish/")
    @Tag(name = "Dishes")
    @Transactional
    public Response addDish(
        @PathParam("restaurantId") Long restaurantId,
        DishDto dto ) {
            Optional<Restaurant> restaurantOptional = Restaurant
                                                        .findByIdOptional(restaurantId);

            if(restaurantOptional.isEmpty()) {
                throw new NotFoundException("This restaurant doesn't exists");
            }

            Dish dish = dishMapper.toDish(dto);
            dish.persist();

            return Response.status(Status.CREATED).entity(dish).build();
    }

    @PUT
    @Path("{restaurantId}/dish/{dishId}")
    @Tag(name = "Dishes")
    @Transactional
    public Response updateDish(
        @PathParam("restaurantId") Long restaurantId,
        @PathParam("id") Long id,
        DishUpdateDto dto ) {
            Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(restaurantId);

            if(restaurantOptional.isEmpty()) {
                throw new NotFoundException("This restaurant doesn't exists");
            }
        
            Optional<Dish> dishOptional = Dish.findByIdOptional(id);
            
            if(dishOptional.isEmpty()){
                throw new NotFoundException("This dish doesn't exists.");
            }

            Dish dish = dishOptional.get();
            dishMapper.updateDishFromDto(dto, dish); 
            dish.persist();

            return Response.status(Status.OK).entity(dish).build();
    }

    @DELETE
    @Path("{restaurantId}/dish/{dishId}")
    @Tag(name = "Dishes")
    @Transactional
    public Response updateDish(
        @PathParam("restaurantId") Long restaurantId,
        @PathParam("id") Long id ) {
            Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(restaurantId);

            if(restaurantOptional.isEmpty()) {
                throw new NotFoundException("This restaurant doesn't exists");
            }
        
            Optional<Dish> dishOptional = Dish.findByIdOptional(id);
            
            dishOptional.ifPresentOrElse(Dish::delete, () -> {
                throw new NotFoundException("This dish doesn't exists");
            });

            return Response.status(Status.OK).build();
    }

}
