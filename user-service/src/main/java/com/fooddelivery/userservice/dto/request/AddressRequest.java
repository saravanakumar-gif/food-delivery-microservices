package com.fooddelivery.userservice.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressRequest {
	private String street;
    private String city;
    private String pincode;
    private Double lat;
    private Double lng;
    private boolean isDefault;

    @JsonCreator
    public AddressRequest(
            @JsonProperty("street") String street,
            @JsonProperty("city") String city,
            @JsonProperty("pincode") String pincode,
            @JsonProperty("lat") Double lat,
            @JsonProperty("lng") Double lng,
            @JsonProperty("isDefault") boolean isDefault) {
        this.street = street;
        this.city = city;
        this.pincode = pincode;
        this.lat = lat;
        this.lng = lng;
        this.isDefault = isDefault;
    }

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
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

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
    
}
