package org.ahmedukamel.gazl.controller.auth;

import jakarta.validation.Valid;
import org.ahmedukamel.gazl.dto.auth.RegisterRequest;
import org.ahmedukamel.gazl.service.auth.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "api/v1/auth")
public class AuthController {
    private final IAuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping(value = "register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.created(URI.create("/api/v1/auth"))
                .body(service.register(request));
    }

    @PostMapping(value = "login")
    public ResponseEntity<?> login(@RequestParam(value = "email") String username,
                                   @RequestParam(value = "password") String password) {
        return ResponseEntity.ok().body(service.login(username, password));
    }
}