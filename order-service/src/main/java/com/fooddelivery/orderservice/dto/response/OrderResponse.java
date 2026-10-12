package com.fooddelivery.orderservice.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fooddelivery.orderservice.model.Order.OrderStatus;
import com.fooddelivery.orderservice.model.Order.PaymentMethod;
import com.fooddelivery.orderservice.model.Order.PaymentStatus;


public class OrderResponse {
	 private UUID id;
	    private UUID customerId;
	    private String customerName;
	    private UUID restaurantId;
	    private String restaurantName;
	    private UUID deliveryBoyId;
	    private String deliveryBoyName;
	    private String deliveryAddress;
	    private List<OrderItemResponse> orderItems;
	    private OrderStatus status;
	    private PaymentStatus paymentStatus;
	    private PaymentMethod paymentMethod;
	    private Double subtotal;
	    private Double discount;
	    private Double deliveryFee;
	    private Double total;
	    private String couponCode;
	    private LocalDateTime createdAt;
		public UUID getId() {
			return id;
		}
		public void setId(UUID id) {
			this.id = id;
		}
		public UUID getCustomerId() {
			return customerId;
		}
		public void setCustomerId(UUID customerId) {
			this.customerId = customerId;
		}
		public String getCustomerName() {
			return customerName;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
		public UUID getRestaurantId() {
			return restaurantId;
		}
		public void setRestaurantId(UUID restaurantId) {
			this.restaurantId = restaurantId;
		}
		public String getRestaurantName() {
			return restaurantName;
		}
		public void setRestaurantName(String restaurantName) {
			this.restaurantName = restaurantName;
		}
		public UUID getDeliveryBoyId() {
			return deliveryBoyId;
		}
		public void setDeliveryBoyId(UUID deliveryBoyId) {
			this.deliveryBoyId = deliveryBoyId;
		}
		public String getDeliveryBoyName() {
			return deliveryBoyName;
		}
		public void setDeliveryBoyName(String deliveryBoyName) {
			this.deliveryBoyName = deliveryBoyName;
		}
		public String getDeliveryAddress() {
			return deliveryAddress;
		}
		public void setDeliveryAddress(String deliveryAddress) {
			this.deliveryAddress = deliveryAddress;
		}
		public List<OrderItemResponse> getOrderItems() {
			return orderItems;
		}
		public void setOrderItems(List<OrderItemResponse> orderItems) {
			this.orderItems = orderItems;
		}
		public OrderStatus getStatus() {
			return status;
		}
		public void setStatus(OrderStatus status) {
			this.status = status;
		}
		public PaymentStatus getPaymentStatus() {
			return paymentStatus;
		}
		public void setPaymentStatus(PaymentStatus paymentStatus) {
			this.paymentStatus = paymentStatus;
		}
		public PaymentMethod getPaymentMethod() {
			return paymentMethod;
		}
		public void setPaymentMethod(PaymentMethod paymentMethod) {
			this.paymentMethod = paymentMethod;
		}
		public Double getSubtotal() {
			return subtotal;
		}
		public void setSubtotal(Double subtotal) {
			this.subtotal = subtotal;
		}
		public Double getDiscount() {
			return discount;
		}
		public void setDiscount(Double discount) {
			this.discount = discount;
		}
		public Double getDeliveryFee() {
			return deliveryFee;
		}
		public void setDeliveryFee(Double deliveryFee) {
			this.deliveryFee = deliveryFee;
		}
		public Double getTotal() {
			return total;
		}
		public void setTotal(Double total) {
			this.total = total;
		}
		public String getCouponCode() {
			return couponCode;
		}
		public void setCouponCode(String couponCode) {
			this.couponCode = couponCode;
		}
		public LocalDateTime getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}
		public OrderResponse(UUID id, UUID customerId, String customerName, UUID restaurantId, String restaurantName,
				UUID deliveryBoyId, String deliveryBoyName, String deliveryAddress, List<OrderItemResponse> orderItems,
				OrderStatus status, PaymentStatus paymentStatus, PaymentMethod paymentMethod, Double subtotal,
				Double discount, Double deliveryFee, Double total, String couponCode, LocalDateTime createdAt) {
			super();
			this.id = id;
			this.customerId = customerId;
			this.customerName = customerName;
			this.restaurantId = restaurantId;
			this.restaurantName = restaurantName;
			this.deliveryBoyId = deliveryBoyId;
			this.deliveryBoyName = deliveryBoyName;
			this.deliveryAddress = deliveryAddress;
			this.orderItems = orderItems;
			this.status = status;
			this.paymentStatus = paymentStatus;
			this.paymentMethod = paymentMethod;
			this.subtotal = subtotal;
			this.discount = discount;
			this.deliveryFee = deliveryFee;
			this.total = total;
			this.couponCode = couponCode;
			this.createdAt = createdAt;
		}
		public OrderResponse() {
			super();
		}
}
