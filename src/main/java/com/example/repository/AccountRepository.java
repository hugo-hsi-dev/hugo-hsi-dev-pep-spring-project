package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    public Account findAccountByUsername(String username);
    public Account findAccountByUsernameAndPassword(String username, String password);
}
