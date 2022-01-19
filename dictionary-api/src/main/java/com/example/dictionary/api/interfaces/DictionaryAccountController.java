package com.example.dictionary.api.interfaces;

import com.example.dictionary.api.dto.CreateAccountDto;
import com.example.dictionary.api.dto.DepositWithdrawAccountDto;
import com.example.dictionary.api.dto.DictionaryAccountDto;
import com.example.dictionary.api.dto.TransferAccountDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface DictionaryAccountController {
    public ResponseEntity<DictionaryAccountDto> create(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody CreateAccountDto createAccountDto);

    public ResponseEntity<DictionaryAccountDto> getEntity(@PathVariable("id") Long id);

    public ResponseEntity<DictionaryAccountDto> deposit(@RequestBody DepositWithdrawAccountDto depositWithdrawAccountDto);

    public ResponseEntity<DictionaryAccountDto> withdraw(@RequestBody DepositWithdrawAccountDto depositWithdrawAccountDto);

    public ResponseEntity<DictionaryAccountDto> transfer(@RequestBody TransferAccountDto transferAccountDto);
}
