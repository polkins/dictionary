package com.example.dictionary.service;

import com.example.dictionary.api.dto.DictionaryAccountDto;

public interface AccountService {
    Long createAccount(DictionaryAccountDto dictionaryAccountDto);
    DictionaryAccountDto getAccount(Long id);
}
