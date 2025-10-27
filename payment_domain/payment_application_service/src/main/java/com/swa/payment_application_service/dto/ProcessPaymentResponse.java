package com.swa.payment_application_service.dto;

import java.util.UUID;

public record ProcessPaymentResponse(UUID paymentId, String message, String status) { }
