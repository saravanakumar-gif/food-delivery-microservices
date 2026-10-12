package com.fooddelivery.restaurantservice.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fooddelivery.restaurantservice.dto.request.MenuItemRequest;
import com.fooddelivery.restaurantservice.dto.request.RestaurantRequest;
import com.fooddelivery.restaurantservice.dto.response.MenuItemResponse;
import com.fooddelivery.restaurantservice.dto.response.RestaurantResponse;
import com.fooddelivery.restaurantservice.model.MenuItem;
import com.fooddelivery.restaurantservice.model.Restaurant;
import com.fooddelivery.restaurantservice.model.Restaurant.RestaurantStatus;
import com.fooddelivery.restaurantservice.repository.MenuItemRepository;
import com.fooddelivery.restaurantservice.repository.RestaurantRepository;

@Service
public class RestaurantService {
	private final RestaurantRepository restaurantRepository;
	private final MenuItemRepository menuItemRepository;

	@Autowired
	public RestaurantService(RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository) {
		this.restaurantRepository = restaurantRepository;
		this.menuItemRepository = menuItemRepository;
	}

	// current logged in user id — JWT filter than SecurityContext la
	// userId ah (email illa) set pannanum, adha namba appuram JwtAuthFilter la pannuvom
	private UUID getCurrentUserId() {
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		return UUID.fromString(userId);
	}

	// Restaurant CRUD

	public RestaurantResponse creatRestaurantResponse(RestaurantRequest request) {
		UUID ownerId = getCurrentUserId();
		Restaurant restaurant = Restaurant.builder()
				.ownerId(ownerId)
				.name(request.getName())
				.cuisineType(request.getCuisineType())
				.address(request.getAddress())
				.city(request.getCity())
				.pincode(request.getPincode())
				.lat(request.getLat())
				.lng(request.getLng())
				.imageUrl(request.getImageUrl())
				.phone(request.getPhone())
				.rating(0.0)
				.totalRatings(0)
				.status(RestaurantStatus.CLOSED)
				.active(true)
				.build();

		restaurantRepository.save(restaurant);
		return mapToRestaurantResponsePublic(restaurant);
	}

	public RestaurantResponse getRestaurantById(UUID id) {
		Restaurant restaurant = restaurantRepository.findByIdAndActiveTrue(id)
				.orElseThrow(() -> new RuntimeException("Restaurant not found!"));
		return mapToRestaurantResponsePublic(restaurant);
	}

	public List<RestaurantResponse> getAllRestaurants() {
		return restaurantRepository.findByActiveTrue()
				.stream()
				.map(this::mapToRestaurantResponsePublic)
				.collect(Collectors.toList());
	}

	public List<RestaurantResponse> searchRestaurants(String keyword) {
		return restaurantRepository.searchRestaurants(keyword)
				.stream()
				.map(this::mapToRestaurantResponsePublic)
				.collect(Collectors.toList());
	}

	public List<RestaurantResponse> getMyRestaurants() {
		UUID ownerId = getCurrentUserId();
		return restaurantRepository.findByOwnerId(ownerId)
				.stream()
				.map(this::mapToRestaurantResponsePublic)
				.collect(Collectors.toList());
	}

	public RestaurantResponse updateRestaurant(UUID id, RestaurantRequest request) {
		UUID ownerId = getCurrentUserId();
		Restaurant restaurant = restaurantRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Restaurant not found!"));

		if (!restaurant.getOwnerId().equals(ownerId)) {
			throw new RuntimeException("Unauthorized!");
		}

		restaurant.setName(request.getName());
		restaurant.setCuisineType(request.getCuisineType());
		restaurant.setAddress(request.getAddress());
		restaurant.setCity(request.getCity());
		restaurant.setPincode(request.getPincode());
		restaurant.setLat(request.getLat());
		restaurant.setLng(request.getLng());
		restaurant.setImageUrl(request.getImageUrl());
		restaurant.setPhone(request.getPhone());

		restaurantRepository.save(restaurant);
		return mapToRestaurantResponsePublic(restaurant);
	}

	public RestaurantResponse toggleStatus(UUID id) {
		UUID ownerId = getCurrentUserId();
		Restaurant restaurant = restaurantRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Unauthorized!"));
		if (!restaurant.getOwnerId().equals(ownerId)) {
			throw new RuntimeException("Unauthorized!");
		}
		restaurant.setStatus(restaurant.getStatus() == RestaurantStatus.OPEN ? RestaurantStatus.CLOSED : RestaurantStatus.OPEN);
		restaurantRepository.save(restaurant);
		return mapToRestaurantResponsePublic(restaurant);
	}

	// menu item crud
	
	public MenuItemResponse getMenuItemById(UUID id) {
	    MenuItem item = menuItemRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Menu item not found!"));
	    return mapToMenuItemResponse(item);
	}
	
	

	public MenuItemResponse addMenuItem(UUID restaurantId, MenuItemRequest request) {
		UUID ownerId = getCurrentUserId();
		Restaurant restaurant = restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new RuntimeException("Restaurant not found!"));

		if (!restaurant.getOwnerId().equals(ownerId)) {
			throw new RuntimeException("Unauthorized!");
		}

		MenuItem item = new MenuItem();
		item.setRestaurant(restaurant);
		item.setName(request.getName());
		item.setDescription(request.getDescription());
		item.setPrice(request.getPrice());
		item.setCategory(request.getCategory());
		item.setImageUrl(request.getImageUrl());
		item.setAvailable(request.isAvailable());
		item.setVeg(request.isVeg());

		menuItemRepository.save(item);
		return mapToMenuItemResponse(item);
	}

	public List<MenuItemResponse> getMenuByRestaurant(UUID restaurantId) {
		Restaurant restaurant = restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new RuntimeException("Restaurant not found!"));
		return menuItemRepository.findByRestaurant(restaurant)
				.stream()
				.map(this::mapToMenuItemResponse)
				.collect(Collectors.toList());
	}

	public MenuItemResponse updateMenuItem(UUID restaurantId, UUID itemId, MenuItemRequest request) {
		UUID ownerId = getCurrentUserId();
		Restaurant restaurant = restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new RuntimeException("Restaurant not found!"));

		if (!restaurant.getOwnerId().equals(ownerId)) {
			throw new RuntimeException("Unauthorized!");
		}

		MenuItem item = menuItemRepository.findByIdAndRestaurant(itemId, restaurant)
				.orElseThrow(() -> new RuntimeException("Menu item not found!"));

		item.setName(request.getName());
		item.setDescription(request.getDescription());
		item.setPrice(request.getPrice());
		item.setCategory(request.getCategory());
		item.setImageUrl(request.getImageUrl());
		item.setAvailable(request.isAvailable());
		item.setVeg(request.isVeg());

		menuItemRepository.save(item);
		return mapToMenuItemResponse(item);
	}

	public void deleteMenuItem(UUID restaurantId, UUID itemId) {
		UUID ownerId = getCurrentUserId();
		Restaurant restaurant = restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new RuntimeException("Restaurant not found!"));

		if (!restaurant.getOwnerId().equals(ownerId)) {
			throw new RuntimeException("Unauthorized!");
		}

		MenuItem item = menuItemRepository.findByIdAndRestaurant(itemId, restaurant)
				.orElseThrow(() -> new RuntimeException("Menu item not found!"));

		menuItemRepository.delete(item);
	}

	// Mappers

	public RestaurantResponse mapToRestaurantResponsePublic(Restaurant restaurant) {
		RestaurantResponse response = new RestaurantResponse();
		response.setId(restaurant.getId());
		response.setOwnerId(restaurant.getOwnerId());
		// ownerName ippo set panna mudiyathu — User Service la irundhu Feign
		// call pannanum, adha namba appuram add pannuvom
		response.setName(restaurant.getName());
		response.setCuisineType(restaurant.getCuisineType());
		response.setAddress(restaurant.getAddress());
		response.setCity(restaurant.getCity());
		response.setPincode(restaurant.getPincode());
		response.setLat(restaurant.getLat());
		response.setLng(restaurant.getLng());
		response.setImageUrl(restaurant.getImageUrl());
		response.setPhone(restaurant.getPhone());
		response.setRating(restaurant.getRating());
		response.setTotalRatings(restaurant.getTotalRatings());
		response.setStatus(restaurant.getStatus());
		response.setCreatedAt(restaurant.getCreatedAt());
		return response;
	}

	private MenuItemResponse mapToMenuItemResponse(MenuItem m) {
		MenuItemResponse response = new MenuItemResponse();
		response.setId(m.getId());
		response.setRestaurantId(m.getRestaurant().getId());
		response.setName(m.getName());
		response.setDescription(m.getDescription());
		response.setPrice(m.getPrice());
		response.setCategory(m.getCategory());
		response.setImageUrl(m.getImageUrl());
		response.setAvailable(m.isAvailable());
		response.setVeg(m.isVeg());
		return response;
	}

}