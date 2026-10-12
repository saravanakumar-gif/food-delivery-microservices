package com.fooddelivery.restaurantservice.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fooddelivery.restaurantservice.model.Restaurant.RestaurantStatus;

public class RestaurantResponse {
	 private UUID id;
	    private String ownerName;
	    private UUID ownerId;
	    private String name;
	    private String cuisineType;
	    private String address;
	    private String city;
	    private String pincode;
	    private Double lat;
	    private Double lng;
	    private String imageUrl;
	    private String phone;
	    private Double rating;
	    private Integer totalRatings;
	    private RestaurantStatus status;
	    private LocalDateTime createdAt;
	    
	    
	    
	    
		public UUID getId() {
			return id;
		}
		public void setId(UUID id) {
			this.id = id;
		}
		public String getOwnerName() {
			return ownerName;
		}
		public void setOwnerName(String ownerName) {
			this.ownerName = ownerName;
		}
		public UUID getOwnerId() {
			return ownerId;
		}
		public void setOwnerId(UUID ownerId) {
			this.ownerId = ownerId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCuisineType() {
			return cuisineType;
		}
		public void setCuisineType(String cuisineType) {
			this.cuisineType = cuisineType;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getPincode() {
			return pincode;
		}
		public void setPincode(String pincode) {
			this.pincode = pincode;
		}
		public Double getLat() {
			return lat;
		}
		public void setLat(Double lat) {
			this.lat = lat;
		}
		public Double getLng() {
			return lng;
		}
		public void setLng(Double lng) {
			this.lng = lng;
		}
		public String getImageUrl() {
			return imageUrl;
		}
		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public Double getRating() {
			return rating;
		}
		public void setRating(Double rating) {
			this.rating = rating;
		}
		public Integer getTotalRatings() {
			return totalRatings;
		}
		public void setTotalRatings(Integer totalRatings) {
			this.totalRatings = totalRatings;
		}
		public RestaurantStatus getStatus() {
			return status;
		}
		public void setStatus(RestaurantStatus status) {
			this.status = status;
		}
		public LocalDateTime getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}
		
		
		
		public RestaurantResponse(UUID id, String ownerName, UUID ownerId, String name, String cuisineType,
				String address, String city, String pincode, Double lat, Double lng, String imageUrl, String phone,
				Double rating, Integer totalRatings, RestaurantStatus status, LocalDateTime createdAt) {
			super();
			this.id = id;
			this.ownerName = ownerName;
			this.ownerId = ownerId;
			this.name = name;
			this.cuisineType = cuisineType;
			this.address = address;
			this.city = city;
			this.pincode = pincode;
			this.lat = lat;
			this.lng = lng;
			this.imageUrl = imageUrl;
			this.phone = phone;
			this.rating = rating;
			this.totalRatings = totalRatings;
			this.status = status;
			this.createdAt = createdAt;
		}
		
		
		
		public RestaurantResponse() {
			super();
		}
	    
}
