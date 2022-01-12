package com.example.dictionary.service;

import com.example.dictionary.api.dto.DictionaryAccountDto;
import com.example.dictionary.domain.entity.account.Account;
import com.example.dictionary.mapper.DictionaryDtoAccountMapper;
import lombok.Data;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

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
    public Optional<DictionaryAccountDto> getAccount(Long id){
        return accountRepository.findById(id)
                .map(mapper::toDto);
    }
}
