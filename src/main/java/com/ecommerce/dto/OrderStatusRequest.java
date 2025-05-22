// backend/src/main/java/com/ecommerce/dto/UpdateOrderStatusRequest.java
package com.ecommerce.dto;

import com.ecommerce.model.OrderStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateOrderStatusRequest {

    @NotNull(message = "Order status is required")
    private OrderStatus newStatus;

    // Getters and Setters
    public OrderStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(OrderStatus newStatus) {
        this.newStatus = newStatus;
    }
}