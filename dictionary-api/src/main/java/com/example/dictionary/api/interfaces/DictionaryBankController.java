package com.example.dictionary.api.interfaces;

import com.example.dictionary.api.dto.DictionaryBankDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface DictionaryBankController {
    public ResponseEntity<Long> create(@RequestBody DictionaryBankDto dictionaryBankDto);
    public ResponseEntity<DictionaryBankDto> getEntity(@PathVariable("id") Long id);
}
