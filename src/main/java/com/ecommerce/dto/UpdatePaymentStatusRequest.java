// backend/src/main/java/com/ecommerce/dto/UpdatePaymentStatusRequest.java
package com.ecommerce.dto;

import com.ecommerce.model.PaymentStatus;
import jakarta.validation.constraints.NotNull;

public class UpdatePaymentStatusRequest {

    @NotNull(message = "Payment status is required")
    private PaymentStatus newStatus;

    // Getters and Setters
    public PaymentStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(PaymentStatus newStatus) {
        this.newStatus = newStatus;
    }
}