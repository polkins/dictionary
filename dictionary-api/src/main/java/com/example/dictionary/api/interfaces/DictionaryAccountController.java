package com.example.dictionary.api.interfaces;

import com.example.dictionary.api.dto.DictionaryAccountDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface DictionaryAccountController {
    public ResponseEntity<Long> create(@RequestBody DictionaryAccountDto dictionaryAccountDto);
    public ResponseEntity<DictionaryAccountDto> getEntity(@PathVariable("id") Long id);
}
