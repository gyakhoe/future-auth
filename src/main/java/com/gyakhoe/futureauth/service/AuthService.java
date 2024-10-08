package com.gyakhoe.futureauth.service;

import com.gyakhoe.futureauth.model.Account;
import com.gyakhoe.futureauth.repository.AuthRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Optional<Account> register(Account user) {
        Optional<Account> existingUser = authRepository.findByUsername(user.getUsername());
        if(existingUser.isPresent()) {
            return Optional.empty();
        }

        // Encrypt the password later
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Account savedAccount = authRepository.save(user);
        return Optional.of(savedAccount);

    }
    public Optional<Account> authenticateWithUsernamePassword(String username, String password) {
        Optional<Account> user = authRepository.findByUsername(username);
        if(user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())){
            return user;
        }
        return Optional.empty();
    }
}
