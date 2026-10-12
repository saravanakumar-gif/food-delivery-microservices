package com.fooddelivery.orderservice.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID customerId;
    private String customerName;      // snapshot

    @Column(nullable = false)
    private UUID restaurantId;
    private String restaurantName;    // snapshot

    private UUID deliveryBoyId;
    private String deliveryBoyName;

    @Column(nullable = false)
    private UUID deliveryAddressId;
    private String deliveryAddressText;   // snapshot ("street, city - pincode")

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    @Enumerated(EnumType.STRING) private OrderStatus status = OrderStatus.PLACED;
    @Enumerated(EnumType.STRING) private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    @Enumerated(EnumType.STRING) private PaymentMethod paymentMethod;

    private Double subtotal;
    private Double discount = 0.0;
    private Double deliveryFee = 30.0;
    private Double total;
    private String couponCode;

    @CreationTimestamp private LocalDateTime createdAt;
    @UpdateTimestamp private LocalDateTime updatedAt;

    public enum OrderStatus { PLACED, CONFIRMED, PREPARING, OUT_FOR_DELIVERY, DELIVERED, CANCELLED }
    public enum PaymentStatus { PENDING, PAID, FAILED, REFUNDED }
    public enum PaymentMethod { CASH_ON_DELIVERY, ONLINE, WALLET }
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
	public UUID getDeliveryAddressId() {
		return deliveryAddressId;
	}
	public void setDeliveryAddressId(UUID deliveryAddressId) {
		this.deliveryAddressId = deliveryAddressId;
	}
	public String getDeliveryAddressText() {
		return deliveryAddressText;
	}
	public void setDeliveryAddressText(String deliveryAddressText) {
		this.deliveryAddressText = deliveryAddressText;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
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
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

   
}