package com.fooddelivery.userservice.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fooddelivery.userservice.dto.request.AddressRequest;
import com.fooddelivery.userservice.dto.request.UpdateProfileRequest;
import com.fooddelivery.userservice.dto.response.AddressResponse;
import com.fooddelivery.userservice.dto.response.UserProfileResponse;
import com.fooddelivery.userservice.model.Address;
import com.fooddelivery.userservice.model.User;
import com.fooddelivery.userservice.repository.AddressRepository;
import com.fooddelivery.userservice.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    // ─── Profile ───────────────────────────────────────────────────

    public UserProfileResponse getProfile() {
        User user = getCurrentUser();
        return mapToProfileResponse(user);
    }

    @Transactional
    public UserProfileResponse updateProfile(UpdateProfileRequest request) {
        User user = getCurrentUser();
        if (request.getName() != null && !request.getName().isEmpty()) {
            user.setName(request.getName());
        }
        if (request.getPhone() != null && !request.getPhone().isEmpty()) {
            user.setPhone(request.getPhone());
        }
        userRepository.save(user);
        return mapToProfileResponse(user);
    }
    
    
    public UserProfileResponse getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        return mapToProfileResponse(user);
    }

    // ─── Address ───────────────────────────────────────────────────
    
    public AddressResponse getAddressById(UUID id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found!"));
        return mapToAddressResponse(address);
    }


    public List<AddressResponse> getAddresses() {
        User user = getCurrentUser();
        return addressRepository.findByUser(user)
                .stream()
                .map(this::mapToAddressResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public AddressResponse addAddress(AddressRequest request) {
        User user = getCurrentUser();

        // Default address set pannuvom podhu — old default remove pannuvoam
        if (request.isDefault()) {
            addressRepository.findByUser(user)
                    .forEach(addr -> {
                        addr.setDefault(false);
                        addressRepository.save(addr);
                    });
        }

        Address address = new Address();
        address.setUser(user);
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setPincode(request.getPincode());
        address.setLat(request.getLat());
        address.setLng(request.getLng());
        address.setDefault(request.isDefault());

        addressRepository.save(address);
        return mapToAddressResponse(address);
    }

    @Transactional
    public void deleteAddress(UUID addressId) {
        User user = getCurrentUser();
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found!"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized!");
        }

        addressRepository.delete(address);
    }
    


    // ─── Mappers ───────────────────────────────────────────────────

    private UserProfileResponse mapToProfileResponse(User user) {
        UserProfileResponse response = new UserProfileResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());
        return response;
    }

    private AddressResponse mapToAddressResponse(Address address) {
        AddressResponse response = new AddressResponse();
        response.setId(address.getId());
        response.setUserId(address.getUser().getId());
        response.setStreet(address.getStreet());
        response.setCity(address.getCity());
        response.setPincode(address.getPincode());
        response.setLat(address.getLat());
        response.setLng(address.getLng());
        response.setDefault(address.isDefault());
        return response;
    }

}
