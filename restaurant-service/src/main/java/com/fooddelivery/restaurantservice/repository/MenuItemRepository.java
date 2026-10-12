package com.fooddelivery.restaurantservice.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddelivery.restaurantservice.model.MenuItem;
import com.fooddelivery.restaurantservice.model.Restaurant;


@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {

	List<MenuItem>findByRestaurant(Restaurant restaurant);
	List<MenuItem> findByRestaurantAndAvailableTrue(Restaurant restaurant);
	List<MenuItem>findByRestaurantAndCategory(Restaurant restaurant, String category);
	Optional<MenuItem>findByIdAndRestaurant(UUID id,Restaurant restaurant);
}
