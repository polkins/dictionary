package com.example.dictionary.repository;

import com.example.dictionary.domain.entity.account.Account;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class AccountRepository extends SimpleJpaRepository<Account, Long> {
    public AccountRepository(EntityManager entityManager){
        super(Account.class, entityManager);
    }
}
