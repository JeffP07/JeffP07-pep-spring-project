package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerUser(Account user) {
        return accountRepository.save(user);
    }

    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public Account login(Account user) {
        Account loginAs = accountRepository.findByUsername(user.getUsername());
        if (loginAs == null) {
            return null;
        }
        if (loginAs.getPassword().equals(user.getPassword())) {
            return loginAs;
        }
        return null;
    }
}
