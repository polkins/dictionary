package com.example.dictionary.controller;

import com.example.dictionary.api.dto.DictionaryAccountDto;
import com.example.dictionary.api.interfaces.DictionaryAccountService;
import com.example.dictionary.service.AccountService;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Data
@RestController
public class DictionaryAccountController implements DictionaryAccountService {
    final AccountService accountService;

    @Override
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create Account")
    public ResponseEntity<Long> create(DictionaryAccountDto dictionaryAccountDto) {
        return ResponseEntity.of(Optional.of(accountService.createAccount(dictionaryAccountDto)));
    }

    @Override
    @PostMapping("/{id}")
    @ApiOperation(value = "Get Account")
    public ResponseEntity<DictionaryAccountDto> getEntity(Long id) {
        return ResponseEntity.of(Optional.of(accountService.getAccount(id)));
    }
}
