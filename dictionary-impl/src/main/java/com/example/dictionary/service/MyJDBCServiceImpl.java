package com.example.dictionary.service;

import com.example.dictionary.domain.entity.account.Account;
import com.example.dictionary.repository.MyJDBCRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MyJDBCServiceImpl implements MyJDBCService{

    private final MyJDBCRepo jdbcRepo;

    public ArrayList<Account> getByIdWithJDBC(Long id) {
        return jdbcRepo.getAccountsByBankIdWithJDBC(id);
    }
}
