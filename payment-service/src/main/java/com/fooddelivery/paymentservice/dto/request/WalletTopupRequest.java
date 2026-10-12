package com.fooddelivery.paymentservice.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WalletTopupRequest {
	 private Double amount;

	    @JsonCreator
	    public WalletTopupRequest(
	            @JsonProperty("amount") Double amount) {
	        this.amount = amount;
	    }

	    public Double getAmount() { return amount; }
	
	
}
