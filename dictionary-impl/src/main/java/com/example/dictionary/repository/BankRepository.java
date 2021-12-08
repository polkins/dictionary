package com.example.dictionary.repository;

import com.example.dictionary.domain.entity.bank.Bank;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public class BankRepository implements CrudRepository<Bank, Long> {
    // TODO: заменить на PagingSortingAndSpecificationRepository
    @Override
    public <S extends Bank> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Bank> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Bank> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Bank> findAll() {
        return null;
    }

    @Override
    public Iterable<Bank> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Bank entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Bank> entities) {

    }

    @Override
    public void deleteAll() {
    }
}
