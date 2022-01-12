package com.example.dictionary.service;

import com.example.dictionary.api.dto.DictionaryBankDto;
import java.util.Optional;

public interface BankService {
     Long createBank(DictionaryBankDto dictionaryBankDto);
     Optional<DictionaryBankDto> getBank(Long id);
}
