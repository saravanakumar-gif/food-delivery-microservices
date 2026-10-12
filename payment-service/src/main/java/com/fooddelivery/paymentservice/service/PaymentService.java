package com.fooddelivery.paymentservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fooddelivery.paymentservice.FeignClient.OrderServiceClient;
import com.fooddelivery.paymentservice.dto.request.PaymentInitiateRequest;
import com.fooddelivery.paymentservice.dto.response.OrderResponse;
import com.fooddelivery.paymentservice.dto.response.PaymentResponse;
import com.fooddelivery.paymentservice.model.Payment;
import com.fooddelivery.paymentservice.model.Payment.PaymentStatus;
import com.fooddelivery.paymentservice.repository.PaymentRepository;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderServiceClient orderServiceClient;

    public PaymentService(PaymentRepository paymentRepository, OrderServiceClient orderServiceClient) {
        this.paymentRepository = paymentRepository;
        this.orderServiceClient = orderServiceClient;
    }

    private UUID getCurrentUserId() {
        return UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Transactional
    public PaymentResponse initiatePayment(PaymentInitiateRequest request) {
        OrderResponse order = orderServiceClient.getOrderById(request.getOrderId()).getData();
        if (order == null) throw new RuntimeException("Order not found!");
        if ("PAID".equals(order.getPaymentStatus())) throw new RuntimeException("Order already paid!");

        Payment payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setCustomerId(order.getCustomerId());
        payment.setAmount(order.getTotal());
        payment.setMethod(Payment.PaymentMethod.ONLINE);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setTransactionRef("SIM-" + UUID.randomUUID());

        paymentRepository.save(payment);
        return mapToResponse(payment);
    }

    @Transactional
    public PaymentResponse verifyPayment(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found!"));

        // Simulate mode — always succeeds. Real gateway integration vandha
        // idha replace pannanum (signature verify).
        payment.setStatus(PaymentStatus.PAID);
        payment.setPaidAt(LocalDateTime.now());
        paymentRepository.save(payment);

        orderServiceClient.updatePaymentStatus(payment.getOrderId(), "PAID");
        return mapToResponse(payment);
    }

    public List<PaymentResponse> getPaymentHistory() {
        UUID customerId = getCurrentUserId();
        return paymentRepository.findByCustomerId(customerId)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private PaymentResponse mapToResponse(Payment p) {
        PaymentResponse r = new PaymentResponse();
        r.setId(p.getId());
        r.setOrderId(p.getOrderId());
        r.setAmount(p.getAmount());
        r.setMethod(p.getMethod());
        r.setStatus(p.getStatus());
        r.setTransactionRef(p.getTransactionRef());
        r.setCreatedAt(p.getCreatedAt());
        r.setPaidAt(p.getPaidAt());
        return r;
    }
}