package com.fooddelivery.restaurantservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public class RestaurantRequest {
	@JsonProperty("name")
    @NotBlank(message = "Name is required")
    private String name;

    @JsonProperty("cuisineType")
    @NotBlank(message = "Cuisine type is required")
    private String cuisineType;

    @JsonProperty("address")
    @NotBlank(message = "Address is required")
    private String address;

    @JsonProperty("city")
    private String city;

    @JsonProperty("pincode")
    private String pincode;

    @JsonProperty("lat")
    private Double lat;

    @JsonProperty("lng")
    private Double lng;

    @JsonProperty("imageUrl")
    private String imageUrl;

    @JsonProperty("phone")
    private String phone;
	
	
	
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
	
	
	public RestaurantRequest(@NotBlank(message = "Name is required") String name,
			@NotBlank(message = "Cuisine type is requried") String cuisineType,
			@NotBlank(message = "Address is requried") String address, String city, String pincode, Double lat,
			Double lng, String imageUrl, String phone) {
		super();
		this.name = name;
		this.cuisineType = cuisineType;
		this.address = address;
		this.city = city;
		this.pincode = pincode;
		this.lat = lat;
		this.lng = lng;
		this.imageUrl = imageUrl;
		this.phone = phone;
	}
	
	public RestaurantRequest() {
		super();
	}
	
}
