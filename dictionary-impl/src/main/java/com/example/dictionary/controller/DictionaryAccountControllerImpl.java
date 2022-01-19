package com.example.dictionary.controller;

import com.example.dictionary.api.dto.CreateAccountDto;
import com.example.dictionary.api.dto.DepositWithdrawAccountDto;
import com.example.dictionary.api.dto.DictionaryAccountDto;
import com.example.dictionary.api.dto.TransferAccountDto;
import com.example.dictionary.api.interfaces.DictionaryAccountController;
import com.example.dictionary.domain.entity.account.Account;
import com.example.dictionary.mapper.DictionaryDtoAccountMapper;
import com.example.dictionary.repository.AccountRepository;
import com.example.dictionary.repository.BankRepository;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.nonsense.auth.client.feign.AuthControllerFeign;

import javax.transaction.Transactional;

import java.util.Random;

import static com.example.dictionary.utils.DictionaryUtils.ACCOUNT;

@RestController
@RequestMapping(ACCOUNT)
@RequiredArgsConstructor
public class DictionaryAccountControllerImpl implements DictionaryAccountController {

    private final AccountRepository accountRepository;
    private final BankRepository bankRepository;
    private final AuthControllerFeign authControllerFeign;
    private final DictionaryDtoAccountMapper mapper;

    @Override
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create account")
    public ResponseEntity<DictionaryAccountDto> create(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody CreateAccountDto createAccountDto) {
        //TODO валидация, что клиент и банк существуют
        var client = authControllerFeign.findClientByInn(authorizationHeader, createAccountDto.getInn()).getBody();
        var bank = bankRepository.findByBic(createAccountDto.getBic());
        var account = accountRepository.save(
                new Account()
                        .setAccountNumber(new Random().ints(20, 0, 9)
                                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                                .toString())
                        .setClientId(client.getId())
                        .setBankId(bank.getId())
                        .setBalance(0.00)
        );

        return ResponseEntity.ok(mapper.toDto(account));
    }

    @Override
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Get account")
    public ResponseEntity<DictionaryAccountDto> getEntity(@PathVariable("id") Long id) {
        return ResponseEntity.ok(mapper.toDto(accountRepository.getById(id)));
    }

    @Override
    @PostMapping(value = "/deposit")
    @ApiOperation(value = "Deposit money into an account")
    public ResponseEntity<DictionaryAccountDto> deposit(@RequestBody DepositWithdrawAccountDto depositWithdrawAccountDto) {
        // TODO: валидация что акк существует
        Account account = accountRepository.findByAccountNumber(depositWithdrawAccountDto.getAccountNumber());
        account.setBalance(account.getBalance() + depositWithdrawAccountDto.getSumm());
        return ResponseEntity.ok(mapper.toDto(accountRepository.save(account)));
    }

    @Override
    @PostMapping(value = "/withdraw")
    @ApiOperation(value = "Withdraw money from the account")
    public ResponseEntity<DictionaryAccountDto> withdraw(@RequestBody DepositWithdrawAccountDto depositWithdrawAccountDto) {
        // TODO: валидация что акк существует
        Account account = accountRepository.findByAccountNumber(depositWithdrawAccountDto.getAccountNumber());
        account.setBalance(account.getBalance() - depositWithdrawAccountDto.getSumm());
        return ResponseEntity.ok(mapper.toDto(accountRepository.save(account)));
    }

    // TODO: вынести в сервис
    @Override
    @Transactional
    @PostMapping(value = "/transfer")
    @ApiOperation(value = "Transfer money from the account to another account")
    public ResponseEntity<DictionaryAccountDto> transfer(@RequestBody TransferAccountDto transferAccountDto) {
        // TODO: валидация что акк существует
        Account withdraw = accountRepository.findByAccountNumber(transferAccountDto.getWithdrawFromAccountNumber());
        Account deposit = accountRepository.findByAccountNumber(transferAccountDto.getDepositIntoAccountNumber());

        var summ = transferAccountDto.getSumm();

        // TODO: валидация
        withdraw.setBalance(withdraw.getBalance() - summ);
        deposit.setBalance(deposit.getBalance() + summ);

        accountRepository.save(withdraw);
        return ResponseEntity.ok(mapper.toDto(accountRepository.save(deposit)));
    }
}
