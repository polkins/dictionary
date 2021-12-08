package com.example.dictionary.controller;

import com.example.dictionary.api.dto.DictionaryBankDto;
import com.example.dictionary.service.BankServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.example.dictionary.utils.DictionaryUtils.API_PREFIX;
import static com.example.dictionary.utils.DictionaryUtils.DICTIONARY;

@Data
@RestController
@RequestMapping(API_PREFIX + DICTIONARY)
public class DictionaryBankController {

    private final BankServiceImpl bankService;

    @PostMapping("/create")
    @ApiOperation(value = "Create Bank")
    public ResponseEntity<Long> create(DictionaryBankDto dictionaryBankDto){
        return ResponseEntity.of(Optional.of(bankService.createBank(dictionaryBankDto)));
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Get Bank")
    public ResponseEntity<DictionaryBankDto> getEntity(@PathVariable("id") Long id){
        return ResponseEntity.of(Optional.of(bankService.getBank(id)));
    }
}
