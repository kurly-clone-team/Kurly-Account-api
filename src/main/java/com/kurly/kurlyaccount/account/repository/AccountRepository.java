package com.kurly.kurlyaccount.account.repository;

import com.kurly.kurlyaccount.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUserId(String userId);
    boolean existsByUserId(String userId);
}
