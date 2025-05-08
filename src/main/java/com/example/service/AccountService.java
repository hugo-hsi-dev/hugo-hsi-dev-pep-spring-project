package com.example.service;

import org.apache.catalina.connector.ClientAbortException;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.InvalidCredentialsException;
import com.example.exception.InvalidDataException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account register(Account account) {
        String username = account.getUsername();
        String password = account.getPassword();

        if (username.length() == 0) {
            throw new InvalidDataException("Username is required");
        }

        if (password.length() < 4) {
            throw new InvalidDataException("Password must be at least 4 characters long");
        }

        Account existingAccount = accountRepository.findAccountByUsername(username);
        if (existingAccount != null) {
            throw new DuplicateUsernameException("Username is taken");
        }

        Account newAccount = accountRepository.save(account);

        return newAccount;
    }

    public Account login(Account account) {
        String username = account.getUsername();
        String password = account.getPassword();

        Account existingAccount = accountRepository.findAccountByUsernameAndPassword(username, password);
        if (existingAccount == null) {
            throw new InvalidCredentialsException("Incorrect username or password");
        }
        return existingAccount;
    }

}
