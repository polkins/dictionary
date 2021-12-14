package com.example.dictionary.controller;

import com.example.dictionary.api.dto.DictionaryAccountDto;
import com.example.dictionary.api.interfaces.DictionaryAccountService;
import com.example.dictionary.service.AccountService;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.example.dictionary.utils.DictionaryUtils.*;

@Data
@RestController
@RequestMapping(API_PREFIX + DICTIONARY + API_PREFIX + ACCOUNT)//todo ?
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
    @GetMapping("/{id}")
    @ApiOperation(value = "Get Account")
    public ResponseEntity<DictionaryAccountDto> getEntity(Long id) {
        return ResponseEntity.of(Optional.of(accountService.getAccount(id)));
    }
}
