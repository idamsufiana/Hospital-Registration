package com.hospital.registration.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiBusinessException.class)
    public ResponseEntity<?> handleBusiness(ApiBusinessException ex) {
        // 501 sesuai spek
        return ResponseEntity.status(501).body(Map.of(
                "timestamp", Instant.now().getEpochSecond(),
                "message", ex.getMessage()
        ));
    }

    // Optional: biarin “mesin” untuk bug, tapi kalau mau rapihin 500 juga boleh:
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAny(Exception ex) {
        return ResponseEntity.status(500).body(Map.of(
                "error", ex.getClass().getSimpleName(),
                "message", ex.getMessage()
        ));
    }
}