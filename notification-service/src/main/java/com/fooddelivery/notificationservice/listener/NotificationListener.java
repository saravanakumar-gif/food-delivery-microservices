package com.fooddelivery.notificationservice.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fooddelivery.notificationservice.event.OrderPlacedEvent;



@Component
public class NotificationListener {
	 @KafkaListener(topics = "order-events", groupId = "notification-group")
	    public void handleOrderPlaced(OrderPlacedEvent event) {
	        System.out.println("📧 Email sent to " + event.getCustomerName()
	                + ": Your order #" + event.getOrderId()
	                + " from " + event.getRestaurantName()
	                + " has been placed! Total: ₹" + event.getTotal());
	    }
}
