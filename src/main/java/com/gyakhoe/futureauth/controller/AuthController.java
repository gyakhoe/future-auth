package com.gyakhoe.futureauth.controller;

import com.gyakhoe.futureauth.model.Account;
import com.gyakhoe.futureauth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Account registerUser) {
        Optional<Account> savedAccount = authService.register(registerUser);
        if(savedAccount.isPresent()) return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount.get());
        else return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exist!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account loginAccount) {

        Optional<Account> authenticatedUser = authService.authenticateWithUsernamePassword(loginAccount.getUsername(),
                loginAccount.getPassword());

        if(authenticatedUser.isPresent()) {
            return  ResponseEntity.ok(authenticatedUser.get());
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
