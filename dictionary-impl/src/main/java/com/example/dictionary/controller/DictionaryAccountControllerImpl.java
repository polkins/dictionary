package com.example.dictionary.controller;

import com.example.dictionary.api.dto.DictionaryAccountDto;
import com.example.dictionary.api.interfaces.DictionaryAccountController;
import com.example.dictionary.service.AccountService;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.dictionary.utils.DictionaryUtils.*;

@Data
@RestController
@RequestMapping(API_PREFIX + DICTIONARY + API_PREFIX + ACCOUNT)//todo ?
public class DictionaryAccountControllerImpl implements DictionaryAccountController {
    final AccountService accountService;

    @Override
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create Account")
    public ResponseEntity<Long> create(DictionaryAccountDto dictionaryAccountDto) {
        return new ResponseEntity<>(accountService.createAccount(dictionaryAccountDto), HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    @ApiOperation(value = "Get Account")
    public ResponseEntity<DictionaryAccountDto> getEntity(Long id) {
        return accountService.getAccount(id)
                .map(acc -> new ResponseEntity<>(acc, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
