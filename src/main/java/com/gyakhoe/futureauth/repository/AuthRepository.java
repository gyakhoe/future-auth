package com.gyakhoe.futureauth.repository;

import com.gyakhoe.futureauth.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Account, Long> {
    public Optional<Account> findByUsername(String username);
}
