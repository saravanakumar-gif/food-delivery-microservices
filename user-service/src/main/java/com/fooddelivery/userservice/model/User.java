package com.fooddelivery.userservice.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

@Id
@GeneratedValue(strategy = GenerationType.UUID)
private UUID id;

@Column(nullable = false)
private String name;

@Column(nullable = false, unique = true)
private String email;

@Column(nullable = false)
private String password;

@Column(nullable = false)
private String phone;

@Enumerated(EnumType.STRING)
@Column(nullable = false)
private Role role;

@Column(nullable = false)
private boolean enabled = true;

@CreationTimestamp
private LocalDateTime createdAt;

private LocalDateTime updatedAt;



public enum Role {
    CUSTOMER,
    RESTAURANT_OWNER,
    DELIVERY_BOY,
    ADMIN
}



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



public String getPassword() {
	return password;
}



public void setPassword(String password) {
	this.password = password;
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



public boolean isEnabled() {
	return enabled;
}



public void setEnabled(boolean enabled) {
	this.enabled = enabled;
}



public LocalDateTime getCreatedAt() {
	return createdAt;
}



public void setCreatedAt(LocalDateTime createdAt) {
	this.createdAt = createdAt;
}



public LocalDateTime getUpdatedAt() {
	return updatedAt;
}



public void setUpdatedAt(LocalDateTime updatedAt) {
	this.updatedAt = updatedAt;
}
}
