package com.fooddelivery.orderservice.dto.request;

import java.util.List;
import java.util.UUID;

import com.fooddelivery.orderservice.model.Order.PaymentMethod;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class OrderRequest {
	 @NotNull(message = "Restaurant is required")
	    private UUID restaurantId;

	    @NotNull(message = "Delivery address is required")
	    private UUID addressId;

	    @NotNull(message = "Payment method is required")
	    private PaymentMethod paymentMethod;

	    private String couponCode;

	    @NotEmpty(message = "Order items are required")
	    private List<OrderItemRequest> items;

		public UUID getRestaurantId() {
			return restaurantId;
		}

		public void setRestaurantId(UUID restaurantId) {
			this.restaurantId = restaurantId;
		}

		public UUID getAddressId() {
			return addressId;
		}

		public void setAddressId(UUID addressId) {
			this.addressId = addressId;
		}

		public PaymentMethod getPaymentMethod() {
			return paymentMethod;
		}

		public void setPaymentMethod(PaymentMethod paymentMethod) {
			this.paymentMethod = paymentMethod;
		}

		public String getCouponCode() {
			return couponCode;
		}

		public void setCouponCode(String couponCode) {
			this.couponCode = couponCode;
		}

		public List<OrderItemRequest> getItems() {
			return items;
		}

		public void setItems(List<OrderItemRequest> items) {
			this.items = items;
		}

		public OrderRequest(@NotNull(message = "Restaurant is required") UUID restaurantId,
				@NotNull(message = "Delivery address is required") UUID addressId,
				@NotNull(message = "Payment method is required") PaymentMethod paymentMethod, String couponCode,
				@NotEmpty(message = "Order items are required") List<OrderItemRequest> items) {
			super();
			this.restaurantId = restaurantId;
			this.addressId = addressId;
			this.paymentMethod = paymentMethod;
			this.couponCode = couponCode;
			this.items = items;
		}

		public OrderRequest() {
			super();
		}
	    
	    
}
