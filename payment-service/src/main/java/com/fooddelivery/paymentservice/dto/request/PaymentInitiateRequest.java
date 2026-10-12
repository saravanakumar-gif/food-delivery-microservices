package com.fooddelivery.paymentservice.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class PaymentInitiateRequest {
	@NotNull(message="Order ID is required")
	private UUID orderId;

	public UUID getOrderId() {
		return orderId;
	}

	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}
}
