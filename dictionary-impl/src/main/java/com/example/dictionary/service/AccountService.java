package com.example.dictionary.service;

import com.example.dictionary.api.dto.DictionaryAccountDto;
import java.util.Optional;

public interface AccountService {
    Long createAccount(DictionaryAccountDto dictionaryAccountDto);
    Optional<DictionaryAccountDto> getAccount(Long id);
}
