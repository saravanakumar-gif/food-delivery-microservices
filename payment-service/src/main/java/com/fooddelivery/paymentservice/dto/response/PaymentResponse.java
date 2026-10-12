package com.fooddelivery.paymentservice.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import com.fooddelivery.paymentservice.model.Payment.PaymentMethod;
import com.fooddelivery.paymentservice.model.Payment.PaymentStatus;

public class PaymentResponse {
    private UUID id;
    private UUID orderId;
    private Double amount;
    private PaymentMethod method;
    private PaymentStatus status;
    private String transactionRef;
    private LocalDateTime createdAt;
    private LocalDateTime paidAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public PaymentMethod getMethod() { return method; }
    public void setMethod(PaymentMethod method) { this.method = method; }
    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }
    public String getTransactionRef() { return transactionRef; }
    public void setTransactionRef(String transactionRef) { this.transactionRef = transactionRef; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getPaidAt() { return paidAt; }
    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }
}