package com.example.week_5.repositories;

import com.example.week_5.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> , JpaSpecificationExecutor<Account> {
  Optional<Account> findByUsername(String username);
}