package com.fooddelivery.paymentservice.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fooddelivery.paymentservice.dto.request.PaymentInitiateRequest;
import com.fooddelivery.paymentservice.dto.response.ApiResponse;
import com.fooddelivery.paymentservice.dto.response.PaymentResponse;
import com.fooddelivery.paymentservice.service.PaymentService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {
    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService) { this.paymentService = paymentService; }

    @PostMapping("/initiate")
    public ResponseEntity<ApiResponse<PaymentResponse>> initiatePayment(@Valid @RequestBody PaymentInitiateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Payment initiated!", paymentService.initiatePayment(request)));
    }

    @PostMapping("/{id}/verify")
    public ResponseEntity<ApiResponse<PaymentResponse>> verifyPayment(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Payment verified!", paymentService.verifyPayment(id)));
    }

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getPaymentHistory() {
        return ResponseEntity.ok(ApiResponse.success("Payment history fetched!", paymentService.getPaymentHistory()));
    }
    
    
}