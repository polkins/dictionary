package com.example.dictionary.repository;

import com.example.dictionary.domain.entity.bank.Bank;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class BankRepository extends SimpleJpaRepository<Bank, Long> {
    public BankRepository(EntityManager em) {
        super(Bank.class, em);
    }
}
