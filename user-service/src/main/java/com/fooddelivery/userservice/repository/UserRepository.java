package com.fooddelivery.userservice.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fooddelivery.userservice.model.User;

public interface UserRepository extends JpaRepository<User,UUID> {
Optional<User> findByEmail (String email);
	
	boolean existsByEmail(String email);
	boolean existsByPhone(String phone);
	
}
