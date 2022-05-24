package com.example.dictionary.service;

import com.example.dictionary.annotation.Audit;
import com.example.dictionary.api.dto.DictionaryBankDto;

public interface BankService {
     @Audit
     Long createBank(DictionaryBankDto dictionaryBankDto);

     @Audit
     DictionaryBankDto getBank(Long id);
}
