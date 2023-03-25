package com.github.gmessiasc.devfood.register.resources;

import java.util.Optional;

import javax.transaction.Transactional;
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

import com.github.gmessiasc.devfood.register.domain.entities.Dish;
import com.github.gmessiasc.devfood.register.domain.entities.Restaurant;

@Path("/restaurants")
@Tag(name = "Restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantResource {
    
    @GET
    public Response getRestaurants() {
        return Response.status(Status.OK)
                    .entity(Restaurant.listAll())
                    .build();
    }

    @POST
    @Transactional
    public Response addRestaurant(Restaurant dto) {
        Restaurant restaurant = new Restaurant();
        restaurant.cnpj = dto.cnpj;
        restaurant.name = dto.name;
        restaurant.owner = dto.owner;
        restaurant.localization = dto.localization;
        restaurant.persist();
        return Response.status(Status.CREATED).entity(restaurant).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateRestaurant(
        @PathParam("id") Long id, 
        Restaurant dto) {
            Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(id);
            
            if(restaurantOptional.isEmpty()){
                throw new NotFoundException();
            }

            Restaurant restaurant = restaurantOptional.get();
            restaurant.name = dto.name;
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

        if(restaurantOptional.isEmpty()) {
            throw new NotFoundException("This restaurant doesn't exists");
        }

        return Response.status(Status.OK)
                    .entity(Dish.list("restaurant", restaurantOptional.get()))
                    .build();
    }

    @POST
    @Path("{restaurantId}/dish/")
    @Tag(name = "Dishes")
    @Transactional
    public Response addDish(
        @PathParam("restaurantId") Long restaurantId,
        Dish dto ) {
            Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(restaurantId);

            if(restaurantOptional.isEmpty()) {
                throw new NotFoundException("This restaurant doesn't exists");
            }

            Dish dish = new Dish();
            dish.name = dto.name;
            dish.description = dto.description;
            dish.price = dto.price;
            dish.restaurant = restaurantOptional.get();
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
        Dish dto ) {
            Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(restaurantId);

            if(restaurantOptional.isEmpty()) {
                throw new NotFoundException("This restaurant doesn't exists");
            }
        
            Optional<Dish> dishOptional = Dish.findByIdOptional(id);
            
            if(dishOptional.isEmpty()){
                throw new NotFoundException("This dish doesn't exists.");
            }

            Dish dish = dishOptional.get();
            dish.price = dto.price;
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
                throw new NotFoundException("This dish doens't exists");
            });

            return Response.status(Status.OK).build();
    }

}
