package com.fooddelivery.userservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.userservice.dto.request.AddressRequest;
import com.fooddelivery.userservice.dto.request.UpdateProfileRequest;
import com.fooddelivery.userservice.dto.response.AddressResponse;
import com.fooddelivery.userservice.dto.response.ApiResponse;
import com.fooddelivery.userservice.dto.response.UserProfileResponse;
import com.fooddelivery.userservice.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
	private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile() {
        UserProfileResponse profile = userService.getProfile();
        return ResponseEntity.ok(
                ApiResponse.success("Profile fetched!", profile));
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request) {
        UserProfileResponse profile = userService.updateProfile(request);
        return ResponseEntity.ok(
                ApiResponse.success("Profile updated!", profile));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getUserById(@PathVariable UUID id) {
        UserProfileResponse profile = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success("User fetched!", profile));
    }

    @GetMapping("/addresses/{id}")
    public AddressResponse getAddressById(@PathVariable UUID id) {
        return userService.getAddressById(id);
    }

    @GetMapping("/addresses")
    public ResponseEntity<ApiResponse<List<AddressResponse>>> getAddresses() {
        List<AddressResponse> addresses = userService.getAddresses();
        return ResponseEntity.ok(
                ApiResponse.success("Addresses fetched!", addresses));
    }

    @PostMapping("/addresses")
    public ResponseEntity<ApiResponse<AddressResponse>> addAddress(
            @Valid @RequestBody AddressRequest request) {
        AddressResponse address = userService.addAddress(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Address added!", address));
    }

    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(
            @PathVariable UUID id) {
        userService.deleteAddress(id);
        return ResponseEntity.ok(
                ApiResponse.success("Address deleted!", null));
    }
}
