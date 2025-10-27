package com.swa.payment_application_service.ports.output;

import java.util.UUID;

public interface JwtPort {
    String generateToken(UUID userId);
    String getSubjectFromToken(String token);
}

