package com.fooddelivery.userservice.dto.response;

import java.util.UUID;

import com.fooddelivery.userservice.model.User.Role;


public class UserProfileResponse {
	private UUID id;
    private String name;
    private String email;
    private String phone;
    private Role role;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
    
}
