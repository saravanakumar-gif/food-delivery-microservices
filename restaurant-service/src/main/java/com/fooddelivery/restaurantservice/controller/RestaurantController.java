package com.fooddelivery.restaurantservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.restaurantservice.dto.request.MenuItemRequest;
import com.fooddelivery.restaurantservice.dto.request.RestaurantRequest;
import com.fooddelivery.restaurantservice.dto.response.ApiResponse;
import com.fooddelivery.restaurantservice.dto.response.MenuItemResponse;
import com.fooddelivery.restaurantservice.dto.response.RestaurantResponse;
import com.fooddelivery.restaurantservice.service.RestaurantService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/restaurants")
@CrossOrigin(origins="*")
public class RestaurantController {
	private final RestaurantService restaurantService;
	@Autowired
	public RestaurantController(RestaurantService restaurantService) {
		this.restaurantService=restaurantService;
	}

	// Public endpoints

	@GetMapping
	public ResponseEntity<ApiResponse<List<RestaurantResponse>>>getAllRestaurants(@RequestParam(required=false) String search,@RequestParam(required=false)String city){
		List<RestaurantResponse>restaurants;
		
		if(search!=null && !search.isEmpty()) {
			restaurants=restaurantService.searchRestaurants(search);
		}else {
			restaurants=restaurantService.getAllRestaurants();
		}
		
		return ResponseEntity.ok(ApiResponse.success("Restaurants fetched!", restaurants));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<RestaurantResponse>> getRestaurantById(@PathVariable UUID id){
		RestaurantResponse restaurant=restaurantService.getRestaurantById(id);
		return ResponseEntity.ok(ApiResponse.success("Restaurant fetched!", restaurant));
	}

	@GetMapping("/{id}/menu")
	public ResponseEntity<ApiResponse<List<MenuItemResponse>>> getMenu(@PathVariable UUID id){
		List<MenuItemResponse> menu=restaurantService.getMenuByRestaurant(id);
		return ResponseEntity.ok(ApiResponse.success("Menu fetched!", menu));
	}

	// Owner endpoints

	@PostMapping
	@PreAuthorize("hasRole('RESTAURANT_OWNER')")
	public ResponseEntity <ApiResponse<RestaurantResponse>> createRestaurant(@Valid @RequestBody RestaurantRequest request){
		RestaurantResponse restaurant= restaurantService.creatRestaurantResponse(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Restaurant created!", restaurant));
		
	}


	@GetMapping("/my-restaurants")
	@PreAuthorize("hasRole('RESTAURANT_OWNER')")
	public ResponseEntity<ApiResponse<List<RestaurantResponse>>> getMyRestaurants(){
		List<RestaurantResponse> restaurants= restaurantService.getMyRestaurants();
		return ResponseEntity.ok(ApiResponse.success("My restaurants fetched!", restaurants));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('RESTAURANT_OWNER')")
	public ResponseEntity<ApiResponse<RestaurantResponse>>updateRestaurant(@PathVariable UUID id, @Valid @RequestBody RestaurantRequest request){
		RestaurantResponse restaurant=restaurantService.updateRestaurant(id,request);
		return ResponseEntity.ok(ApiResponse.success("Restaurant updated!", restaurant));
	}

	@PatchMapping("/{id}/status")
	@PreAuthorize("hasRole('RESTAURANT_OWNER')")
	public ResponseEntity<ApiResponse<RestaurantResponse>> toggleStatus(@PathVariable UUID id){
		RestaurantResponse restaurant=restaurantService.toggleStatus(id);
		return ResponseEntity.ok(ApiResponse.success("Status updated!", restaurant));
	}


	// Menu endpoints
	
	@GetMapping("/menu-items/{id}")
	public ResponseEntity<ApiResponse<MenuItemResponse>> getMenuItemById(@PathVariable UUID id) {

	    System.out.println("🔥 MENU ITEM ENDPOINT HIT: " + id);

	    MenuItemResponse item = restaurantService.getMenuItemById(id);

	    return ResponseEntity.ok(
	            ApiResponse.success("Menu item fetched!", item)
	    );
	}
	
	@PostMapping("/{id}/menu")
	@PreAuthorize("hasRole('RESTAURANT_OWNER')")
	public ResponseEntity<ApiResponse<MenuItemResponse>>addMenuItem(@PathVariable UUID id,@Valid  @RequestBody MenuItemRequest request){
		MenuItemResponse item=restaurantService.addMenuItem(id, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Menu item added!", item));
	}


	@PutMapping("/{id}/menu/{itemId}")
	@PreAuthorize("hasRole('RESTAURANT_OWNER')")
	public ResponseEntity<ApiResponse<MenuItemResponse>> updateMenuItem(
	        @PathVariable UUID id,
	        @PathVariable UUID itemId,
	        @Valid @RequestBody MenuItemRequest request) {
	    MenuItemResponse item = restaurantService.updateMenuItem(id, itemId, request);
	    return ResponseEntity.ok(
	            ApiResponse.success("Menu item updated!", item));
	}

	@DeleteMapping("/{id}/menu/{itemId}")
	@PreAuthorize("hasRole('RESTAURANT_OWNER')")
	public ResponseEntity<ApiResponse<Void>> deleteMenuItem(
	        @PathVariable UUID id,
	        @PathVariable UUID itemId) {
	    restaurantService.deleteMenuItem(id, itemId);
	    return ResponseEntity.ok(
	            ApiResponse.success("Menu item deleted!", null));
	}

}
