package com.fooddelivery.userservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fooddelivery.userservice.model.Address;
import com.fooddelivery.userservice.model.User;

public interface AddressRepository extends JpaRepository<Address, UUID> {
	 List<Address> findByUser(User user);
}
