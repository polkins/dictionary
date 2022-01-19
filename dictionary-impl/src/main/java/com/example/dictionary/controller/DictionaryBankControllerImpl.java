package com.example.dictionary.controller;

import com.example.dictionary.api.dto.DictionaryBankDto;
import com.example.dictionary.api.interfaces.DictionaryBankController;
import com.example.dictionary.service.BankService;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.example.dictionary.utils.DictionaryUtils.BANK;

@Data
@RestController
@RequestMapping(BANK)
public class DictionaryBankControllerImpl implements DictionaryBankController {

    private final BankService bankService;

    @Override
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create Bank")
    public ResponseEntity<Long> create(@RequestBody DictionaryBankDto dictionaryBankDto){
        return ResponseEntity.of(Optional.of(bankService.createBank(dictionaryBankDto)));
    }

    @Override
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Get Bank")
    public ResponseEntity<DictionaryBankDto> getEntity(@PathVariable("id") Long id){
        return bankService.getBank(id)
                .map(bank -> new ResponseEntity<>(bank, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
