package com.example.dictionary.service;

import com.example.dictionary.domain.entity.account.Account;

import java.util.ArrayList;

public interface MyJDBCService {
    ArrayList<Account> getByIdWithJDBC(Long id);
}
