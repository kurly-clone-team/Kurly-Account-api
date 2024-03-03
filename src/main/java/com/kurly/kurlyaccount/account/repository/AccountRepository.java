package com.kurly.kurlyaccount.account.repository;

import com.kurly.kurlyaccount.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
