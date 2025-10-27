package com.swa.payment_application_service.handler;

import com.swa.payment_application_service.ports.output.JwtPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtHandler{

//    private final JwtPort jwtPort;
//
//
//    public String createTokenForUser(User user) {
//        return jwtPort.generateToken(user.getId().getValue());
//    }
//
//    public UUID decodeTokenToCustomerId(String token) {
//        String subject = jwtPort.getSubjectFromToken(token);
//        return UUID.fromString(subject);
//    }
}
