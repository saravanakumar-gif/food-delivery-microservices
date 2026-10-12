package com.fooddelivery.paymentservice.FeignClient;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.fooddelivery.paymentservice.dto.response.FeignApiResponse;
import com.fooddelivery.paymentservice.dto.response.OrderResponse;

@FeignClient(name = "ORDER-SERVICE")
public interface OrderServiceClient {
	@GetMapping("/api/orders/{id}")
    FeignApiResponse<OrderResponse> getOrderById(@PathVariable UUID id);

    @PatchMapping("/api/orders/{id}/payment-status")
    void updatePaymentStatus(@PathVariable UUID id, @RequestParam String status);
}
