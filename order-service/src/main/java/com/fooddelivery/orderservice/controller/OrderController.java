package com.fooddelivery.orderservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.orderservice.dto.request.OrderRequest;
import com.fooddelivery.orderservice.dto.response.ApiResponse;
import com.fooddelivery.orderservice.dto.response.OrderResponse;
import com.fooddelivery.orderservice.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins="*")
public class OrderController {
	private final OrderService orderService;

	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService=orderService;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<OrderResponse>> placeOrder(@Valid @RequestBody OrderRequest request){
		OrderResponse order= orderService.placeOrder(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Order placed successfully!", order));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<OrderResponse>>> getMyOrders(){
		List<OrderResponse>orders=orderService.getMyOrders();
		return ResponseEntity.ok(ApiResponse.success("Orders fetched!", orders));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<OrderResponse>>getOrderById(@PathVariable UUID id){
		OrderResponse order=orderService.getOrderById(id);
		return ResponseEntity.ok(ApiResponse.success("Order status updated!", order));
	}



	@PatchMapping("/{id}/status")
	public ResponseEntity<ApiResponse<OrderResponse>> updateStatus(@PathVariable UUID id, @RequestParam String status){
		OrderResponse order=orderService.updateOrderStatus(id, status);
		return ResponseEntity.ok(ApiResponse.success("Order status updated!", order));
	}


	@PatchMapping("/{id}/cancel")
	public ResponseEntity<ApiResponse<OrderResponse>> cancelOrder(
	        @PathVariable UUID id) {
	    OrderResponse order = orderService.cancelOrder(id);
	    return ResponseEntity.ok(
	            ApiResponse.success("Order cancelled!", order));
	}
	
	@PatchMapping("/{id}/payment-status")
	public ResponseEntity<ApiResponse<Void>> updatePaymentStatus(@PathVariable UUID id, @RequestParam String status) {
	    orderService.updatePaymentStatus(id, status);
	    return ResponseEntity.ok(ApiResponse.success("Payment status updated!", null));
	}


}
