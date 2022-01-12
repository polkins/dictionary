package com.example.dictionary.repository;

import com.example.dictionary.domain.entity.bank.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
    Bank findByBic(String bic);
}
