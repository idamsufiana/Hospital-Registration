package com.hospital.registration.controller;

import com.hospital.registration.dto.*;
import com.hospital.registration.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController extends BaseController{

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/init-data")
    public ResponseEntity<?> init(@RequestBody InitDataRequest req) {
        return ok(authService.initData(req));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        return ok(authService.login(req));
    }

    @PostMapping("/ubah-password-sendiri")
    public ResponseEntity<?> ubahPassword(@RequestBody UbahPasswordRequest req, Authentication auth) {
        String userId = auth.getName();
        return ok(authService.ubahPasswordSendiri(userId, req));
    }
}