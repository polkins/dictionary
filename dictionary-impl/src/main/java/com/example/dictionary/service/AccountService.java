package com.example.dictionary.service;

import com.example.dictionary.api.dto.DictionaryAccountDto;
import com.example.dictionary.exceptions.NotFoundEntityException;

public interface AccountService {
    Long createAccount(DictionaryAccountDto dictionaryAccountDto);
    DictionaryAccountDto getAccount(Long id) throws NotFoundEntityException;
}
