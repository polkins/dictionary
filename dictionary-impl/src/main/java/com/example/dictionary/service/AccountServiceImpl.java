package com.example.dictionary.service;

import com.example.dictionary.api.dto.DictionaryAccountDto;
import com.example.dictionary.domain.entity.account.Account;
import com.example.dictionary.exceptions.NotFoundEntityException;
import com.example.dictionary.mapper.DictionaryDtoAccountMapper;
import lombok.Data;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Data
@Service
public class AccountServiceImpl implements AccountService {

    private final CrudRepository<Account, Long> accountRepository;
    private final DictionaryDtoAccountMapper mapper;

    @Override
    public Long createAccount(DictionaryAccountDto dictionaryAccountDto) {
        return accountRepository.save(mapper.toDomainModel(dictionaryAccountDto)).getId();
    }

    @Override
    public DictionaryAccountDto getAccount(Long id) throws NotFoundEntityException {

        DictionaryAccountDto accDt = accountRepository.findById(id)
                .map(mapper::toDto)
                .get();
        if (accDt == null) {
            //todo или в лог без ошибок?
            throw new NotFoundEntityException("Not found account with id = " + id);
        }
        return accDt;
    }
}
