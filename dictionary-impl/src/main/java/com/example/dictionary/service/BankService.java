package com.example.dictionary.service;

import com.example.dictionary.api.dto.DictionaryBankDto;

public interface BankService {
     Long createBank(DictionaryBankDto dictionaryBankDto);

     DictionaryBankDto getBank(Long id);

     DictionaryBankDto getBank1(Long id);
}
