package com.fooddelivery.restaurantservice.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fooddelivery.restaurantservice.model.Restaurant;
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID>{
	List<Restaurant>findByActiveTrue();
	List<Restaurant>findByOwnerId(UUID ownerId);
	Optional<Restaurant>findByIdAndActiveTrue(UUID id);

	@Query("SELECT r FROM Restaurant r WHERE r.active = true AND " +
	        "(LOWER(r.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	        "LOWER(r.cuisineType) LIKE LOWER(CONCAT('%', :keyword, '%')))")
	List<Restaurant>searchRestaurants(@Param("keyword")String keyword);
	@Query("SELECT r FROM Restaurant r WHERE r.active = true AND r.city = :city")
	List<Restaurant>findByCity(@Param("city")String city);

}
