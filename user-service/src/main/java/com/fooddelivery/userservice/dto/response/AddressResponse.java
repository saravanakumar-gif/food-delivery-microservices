package com.fooddelivery.userservice.dto.response;

import java.util.UUID;

public class AddressResponse {
	 private UUID id;
	 private UUID userId;
	    public UUID getUserId() {
		return userId;
	}
	 public void setUserId(UUID userId) {
		 this.userId = userId;
	 }
		private String street;
	    private String city;
	    private String pincode;
	    private Double lat;
	    private Double lng;
	    private boolean isDefault;
		public UUID getId() {
			return id;
		}
		public void setId(UUID id) {
			this.id = id;
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
