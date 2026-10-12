package com.fooddelivery.orderservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fooddelivery.orderservice.dto.request.OrderItemRequest;
import com.fooddelivery.orderservice.dto.request.OrderRequest;
import com.fooddelivery.orderservice.dto.response.AddressResponse;
import com.fooddelivery.orderservice.dto.response.MenuItemResponse;
import com.fooddelivery.orderservice.dto.response.OrderItemResponse;
import com.fooddelivery.orderservice.dto.response.OrderResponse;
import com.fooddelivery.orderservice.dto.response.RestaurantResponse;
import com.fooddelivery.orderservice.dto.response.UserResponse;
import com.fooddelivery.orderservice.event.OrderPlacedEvent;
import com.fooddelivery.orderservice.feignclient.RestaurantServiceClient;
import com.fooddelivery.orderservice.feignclient.UserServiceClient;
import com.fooddelivery.orderservice.model.Order;
import com.fooddelivery.orderservice.model.Order.OrderStatus;
import com.fooddelivery.orderservice.model.OrderItem;
import com.fooddelivery.orderservice.repository.OrderItemRepository;
import com.fooddelivery.orderservice.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final RestaurantServiceClient restaurantServiceClient;
	private final UserServiceClient userServiceClient;
	private final KafkaTemplate<String, Object> kafkaTemplate;

	public OrderService(OrderRepository orderRepository,
			OrderItemRepository orderItemRepository,
			RestaurantServiceClient restaurantServiceClient,
			UserServiceClient userServiceClient,
			KafkaTemplate<String, Object> kafkaTemplate) {
		this.orderRepository = orderRepository;
		this.orderItemRepository = orderItemRepository;
		this.restaurantServiceClient = restaurantServiceClient;
		this.userServiceClient = userServiceClient;
		this.kafkaTemplate = kafkaTemplate;
	}

	
	private UUID getCurrentUserId() {
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		return UUID.fromString(userId);
	}

	private String getCurrentUserRole() {
		return SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
	}

	@Transactional
	public OrderResponse placeOrder(OrderRequest request) {
		UUID customerId = getCurrentUserId();

		// Restaurant Service ku Feign call
		RestaurantResponse restaurant = restaurantServiceClient.getRestaurantById(request.getRestaurantId()).getData();
		if (restaurant == null) {
			throw new RuntimeException("Restaurant not found!");
		}
		if ("CLOSED".equals(restaurant.getStatus())) {
			throw new RuntimeException("Restaurant is currently closed!");
		}

		// User Service ku Feign call - customer name + address
		UserResponse customer = userServiceClient.getUserById(customerId).getData();

		if (customer == null) {
		    throw new RuntimeException("Customer not found!");
		}
		AddressResponse address = userServiceClient
		        .getAddressById(request.getAddressId());

		if (address == null) {
		    throw new RuntimeException("Address not found!");
		}

		System.out.println("Customer ID: " + customerId);
		System.out.println("Address ID: " + address.getId());
		System.out.println("Address User ID: " + address.getUserId());

		if (!customerId.equals(address.getUserId())) {
		    throw new RuntimeException("Unauthorized address!");
		}
		Order order = new Order();
		order.setCustomerId(customerId);
		order.setCustomerName(customer.getName());
		order.setRestaurantId(restaurant.getId());
		order.setRestaurantName(restaurant.getName());
		order.setDeliveryAddressId(address.getId());
		order.setDeliveryAddressText(address.getStreet() + ", " + address.getCity() + " - " + address.getPincode());
		order.setPaymentMethod(request.getPaymentMethod());
		order.setStatus(OrderStatus.PLACED);
		order.setCouponCode(request.getCouponCode());

		List<OrderItem> orderItems = new ArrayList<>();
		double subtotal = 0.0;

		for (OrderItemRequest itemRequest : request.getItems()) {
			// Restaurant Service ku Feign call - each menu item
			MenuItemResponse menuItem = restaurantServiceClient.getMenuItemById(itemRequest.getMenuItemId()).getData();
			if (menuItem == null) {
				throw new RuntimeException("Menu item not found: " + itemRequest.getMenuItemId());
			}
			if (!menuItem.isAvailable()) {
				throw new RuntimeException(menuItem.getName() + " is currently unavailable!");
			}
			if (!menuItem.getRestaurantId().equals(restaurant.getId())) {
				throw new RuntimeException("Menu item does not belong to this restaurant!");
			}

			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);
			orderItem.setMenuItemId(menuItem.getId());
			orderItem.setMenuItemName(menuItem.getName());
			orderItem.setQuantity(itemRequest.getQuantity());
			orderItem.setPriceAtOrder(menuItem.getPrice());

			subtotal += menuItem.getPrice() * itemRequest.getQuantity();
			orderItems.add(orderItem);
		}

		order.setSubtotal(subtotal);
		order.setDiscount(0.0);
		order.setDeliveryFee(30.0);
		order.setTotal(subtotal + 30.0);
		order.setOrderItems(orderItems);

		orderRepository.save(order);
		orderItemRepository.saveAll(orderItems);

		kafkaTemplate.send("order-events", new OrderPlacedEvent(
			    order.getId(), order.getCustomerId(), order.getCustomerName(),
			    order.getRestaurantName(), order.getTotal()
			));

			return mapToOrderResponse(order);
	}

	public List<OrderResponse> getMyOrders() {
		UUID userId = getCurrentUserId();
		String role = getCurrentUserRole();

		switch (role) {
			case "CUSTOMER":
				return orderRepository.findByCustomerIdOrderByCreatedAtDesc(userId)
						.stream().map(this::mapToOrderResponse).collect(Collectors.toList());
			case "RESTAURANT_OWNER":
				// Ippodhaikku simple ah — restaurantId list Feign vechi fetch pannalam (extend pannalam appuram)
				return orderRepository.findAll()
						.stream().map(this::mapToOrderResponse).collect(Collectors.toList());
			case "DELIVERY_BOY":
				return orderRepository.findByDeliveryBoyIdOrderByCreatedAtDesc(userId)
						.stream().map(this::mapToOrderResponse).collect(Collectors.toList());
			default:
				return orderRepository.findAll()
						.stream().map(this::mapToOrderResponse).collect(Collectors.toList());
		}
	}
	
	

	public OrderResponse getOrderById(UUID orderId) {
		UUID userId = getCurrentUserId();
		String role = getCurrentUserRole();
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found!"));

		boolean isCustomer = order.getCustomerId().equals(userId);
		boolean isDelivery = order.getDeliveryBoyId() != null && order.getDeliveryBoyId().equals(userId);
		boolean isAdmin = "ADMIN".equals(role);
		boolean isOwner = "RESTAURANT_OWNER".equals(role); // exact ownership check Feign vechi extend pannalam

		if (!isCustomer && !isOwner && !isDelivery && !isAdmin) {
			throw new RuntimeException("Unauthorized!");
		}
		return mapToOrderResponse(order);
	}

	@Transactional
	public OrderResponse updateOrderStatus(UUID orderId, String status) {
		String role = getCurrentUserRole();
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found!"));

		OrderStatus newStatus = OrderStatus.valueOf(status);

		switch (role) {
			case "RESTAURANT_OWNER":
				if (newStatus != OrderStatus.CONFIRMED && newStatus != OrderStatus.PREPARING
						&& newStatus != OrderStatus.CANCELLED) {
					throw new RuntimeException("Invalid status for restaurant owner!");
				}
				break;
			case "DELIVERY_BOY":
				if (newStatus != OrderStatus.OUT_FOR_DELIVERY && newStatus != OrderStatus.DELIVERED) {
					throw new RuntimeException("Invalid status for delivery boy!");
				}
				break;
			case "ADMIN":
				break;
			default:
				throw new RuntimeException("Unauthorized!");
		}

		order.setStatus(newStatus);
		orderRepository.save(order);
		return mapToOrderResponse(order);
	}

	@Transactional
	public OrderResponse cancelOrder(UUID orderId) {
		UUID customerId = getCurrentUserId();
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found!"));

		if (!order.getCustomerId().equals(customerId)) {
			throw new RuntimeException("Unauthorized!");
		}
		if (order.getStatus() != OrderStatus.PLACED) {
			throw new RuntimeException("Order cannot be cancelled after confirmation!");
		}
		order.setStatus(OrderStatus.CANCELLED);
		orderRepository.save(order);
		return mapToOrderResponse(order);
	}
	
	@Transactional
	public void updatePaymentStatus(UUID orderId, String status) {
	    Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found!"));
	    order.setPaymentStatus(Order.PaymentStatus.valueOf(status));
	    orderRepository.save(order);
	}

	private OrderResponse mapToOrderResponse(Order order) {
		OrderResponse response = new OrderResponse();
		response.setId(order.getId());
		response.setCustomerId(order.getCustomerId());
		response.setCustomerName(order.getCustomerName());
		response.setRestaurantId(order.getRestaurantId());
		response.setRestaurantName(order.getRestaurantName());
		response.setDeliveryBoyId(order.getDeliveryBoyId());
		response.setDeliveryBoyName(order.getDeliveryBoyName());
		response.setDeliveryAddress(order.getDeliveryAddressText());
		response.setStatus(order.getStatus());
		response.setPaymentStatus(order.getPaymentStatus());
		response.setPaymentMethod(order.getPaymentMethod());
		response.setSubtotal(order.getSubtotal());
		response.setDiscount(order.getDiscount());
		response.setDeliveryFee(order.getDeliveryFee());
		response.setTotal(order.getTotal());
		response.setCouponCode(order.getCouponCode());
		response.setCreatedAt(order.getCreatedAt());

		if (order.getOrderItems() != null) {
			List<OrderItemResponse> itemResponses = order.getOrderItems().stream()
					.map(this::mapToOrderItemResponse).collect(Collectors.toList());
			response.setOrderItems(itemResponses);
		}
		return response;
	}

	private OrderItemResponse mapToOrderItemResponse(OrderItem item) {
		OrderItemResponse response = new OrderItemResponse();
		response.setId(item.getId());
		response.setMenuItemId(item.getMenuItemId());
		response.setMenuItemName(item.getMenuItemName());
		response.setQuantity(item.getQuantity());
		response.setPriceAtOrder(item.getPriceAtOrder());
		response.setItemTotal(item.getPriceAtOrder() * item.getQuantity());
		return response;
	}
}