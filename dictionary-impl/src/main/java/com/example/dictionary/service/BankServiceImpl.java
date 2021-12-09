package com.example.dictionary.service;

import com.example.dictionary.api.dto.DictionaryBankDto;
import com.example.dictionary.domain.entity.bank.Bank;
import com.example.dictionary.mapper.DictionaryDtoBankMapper;
import lombok.Data;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
@Data
public class BankServiceImpl implements BankService {

    private final CrudRepository<Bank, Long> bankRepository;
    private final DictionaryDtoBankMapper mapper;

    @Override
    public Long createBank(DictionaryBankDto dictionaryBankDto) {
        return bankRepository.save(mapper.toDomainModel(dictionaryBankDto)).getId();
    }

    @Override
    public DictionaryBankDto getBank(Long id) {
        return bankRepository.findById(id)
                .map(mapper::toDto)
                .orElse(new DictionaryBankDto());
    }
}
