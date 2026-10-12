package com.fooddelivery.orderservice.feignclient;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fooddelivery.orderservice.config.FeignConfig;
import com.fooddelivery.orderservice.dto.response.AddressResponse;
import com.fooddelivery.orderservice.dto.response.UserResponse;

@FeignClient(
    name = "USER-SERVICE",
    configuration = FeignConfig.class
)
public interface UserServiceClient {

    @GetMapping("/api/users/{id}")
    FeignApiResponse<UserResponse> getUserById(@PathVariable UUID id);

    @GetMapping("/api/users/addresses/{id}")
    AddressResponse getAddressById(@PathVariable UUID id);
}