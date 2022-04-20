package com.example.dictionary;

import com.example.dictionary.api.dto.CreateAccountDto;
import com.example.dictionary.api.dto.DepositWithdrawAccountDto;
import com.example.dictionary.api.dto.DictionaryAccountDto;
import com.example.dictionary.api.dto.DictionaryBankDto;
import com.example.dictionary.api.dto.TransferAccountDto;
import com.example.dictionary.common.AbstractIntegrationTest;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;
import ru.nonsense.auth.client.api.dto.ClientDto;
import ru.nonsense.auth.client.feign.AuthControllerFeign;

import java.util.Random;
import java.util.stream.Collectors;

import static com.example.dictionary.ParametrizedTypeReferenceHolder.ACCOUNT_TYPE_REFERENCE;
import static com.example.dictionary.ParametrizedTypeReferenceHolder.DICTIONARY_BANK_TYPE_REFERENCE;
import static com.example.dictionary.ParametrizedTypeReferenceHolder.ID_TYPE_REFERENCE;
import static com.example.dictionary.utils.DictionaryUtils.ACCOUNT;
import static com.example.dictionary.utils.DictionaryUtils.BANK;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

public class DictionaryImplApplicationTests extends AbstractIntegrationTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    private AuthControllerFeign authControllerFeign;

    @Test
    public void createAndGetEntity() {
        DictionaryBankDto dictionaryBankDto = getDictionaryBankDto();

        HttpEntity<?> request = new HttpEntity<>(dictionaryBankDto, getHeaders());

        ResponseEntity<Long> createdBank = restTemplate.exchange(
                BANK + "/create",
                HttpMethod.POST,
                request,
                ID_TYPE_REFERENCE
        );

        assertThat(createdBank.getBody()).isNotNull();
        Long id = createdBank.getBody();
        assertThat(id).isNotNull();

        HttpEntity<?> request2 = new HttpEntity<>(getHeaders());

        var selectedBank = restTemplate.exchange(
                BANK + "/{id}",
                HttpMethod.GET,
                request2,
                DICTIONARY_BANK_TYPE_REFERENCE,
                id
        );

        DictionaryBankDto bankBody = selectedBank.getBody();
        assertThat(bankBody).isNotNull();
        assertThat(bankBody.getAddress()).isEqualTo(dictionaryBankDto.getAddress());
        assertThat(bankBody.getBic()).isEqualTo(dictionaryBankDto.getBic());
        assertThat(bankBody.getName()).isEqualTo(dictionaryBankDto.getName());
    }

    @Test
    public void transferMoneyFromOneAccountToAnother() {
        var accounts = createAccountsAndBank();

        var accountNumberIvanov = accounts.getLeft().getAccountNumber();
        var accountNumberNotIvanov = accounts.getRight().getAccountNumber();

        var transferAccountDto = new TransferAccountDto()
                .setWithdrawFromAccountNumber(accountNumberNotIvanov)
                .setDepositIntoAccountNumber(accountNumberIvanov)
                .setSumm(20.00);

        var transferResponse = restTemplate.exchange(
                ACCOUNT + "/transfer",
                HttpMethod.POST,
                new HttpEntity<>(transferAccountDto, getHeaders()),
                ACCOUNT_TYPE_REFERENCE
        );

        assertThat(transferResponse.getBody()).isNotNull();
        var balanceIvanov = transferResponse.getBody().getBalance();
        assertThat(balanceIvanov).isEqualTo(20.00);

        var notIvanov = restTemplate.exchange(
                ACCOUNT + "/{id}",
                HttpMethod.GET,
                new HttpEntity<>(getHeaders()),
                ACCOUNT_TYPE_REFERENCE,
                accounts.getRight().getId()
        );

        assertThat(notIvanov.getBody()).isNotNull();
        var balanceNotIvanov = notIvanov.getBody().getBalance();
        assertThat(balanceNotIvanov).isEqualTo(30.00);
    }

    @Test
    public void getByIdWithJDBC(){
        var accounts = createAccountsAndBank();

        var accountNumberIvanov = accounts.getLeft();
        var accountNumberNotIvanov = accounts.getRight();

        var bankId = accountNumberIvanov.getBankId();

        var jdbcAccounts = myJDBCService.getByIdWithJDBC(bankId);

        assertThat(jdbcAccounts.size()).isEqualTo(2);

        assertThat(jdbcAccounts.get(0).getId()).isEqualTo(accountNumberIvanov.getId());
        assertThat(jdbcAccounts.get(0).getAccountNumber()).isEqualTo(accountNumberIvanov.getAccountNumber());
        assertThat(jdbcAccounts.get(0).getBankId()).isEqualTo(accountNumberIvanov.getBankId());
        assertThat(jdbcAccounts.get(0).getClientId()).isEqualTo(accountNumberIvanov.getClientId());

        assertThat(jdbcAccounts.get(1).getId()).isEqualTo(accountNumberNotIvanov.getId());
        assertThat(jdbcAccounts.get(1).getAccountNumber()).isEqualTo(accountNumberNotIvanov.getAccountNumber());
        assertThat(jdbcAccounts.get(1).getBankId()).isEqualTo(accountNumberNotIvanov.getBankId());
        assertThat(jdbcAccounts.get(1).getClientId()).isEqualTo(accountNumberNotIvanov.getClientId());
    }

    private Pair<DictionaryAccountDto, DictionaryAccountDto> createAccountsAndBank() {
        DictionaryBankDto dictionaryBankDto = getDictionaryBankDto();

        var clientIvanov = getClientDto("Иванов", "Иван", "Иванович", "644901001", 1L);
        var clientNotIvanov = getClientDto("НеИванов", "НеИван", "НеИванович", "644901002", 2L);

        var createdBank = restTemplate.exchange(
                BANK + "/create",
                HttpMethod.POST,
                new HttpEntity<>(dictionaryBankDto, getHeaders()),
                ID_TYPE_REFERENCE
        );

        assertThat(createdBank.getBody()).isNotNull();
        var id = createdBank.getBody();
        assertThat(id).isNotNull();

        when(authControllerFeign.findClientByInn("auth", clientIvanov.getInn())).thenReturn(ResponseEntity.ok(clientIvanov));
        when(authControllerFeign.findClientByInn("auth", clientNotIvanov.getInn())).thenReturn(ResponseEntity.ok(clientNotIvanov));

        var createAccountIvanov = new CreateAccountDto()
                .setBic(dictionaryBankDto.getBic())
                .setInn(clientIvanov.getInn());

        var createAccountNotIvanov = new CreateAccountDto()
                .setBic(dictionaryBankDto.getBic())
                .setInn(clientNotIvanov.getInn());

        var createdAccountIvanov = restTemplate.exchange(
                ACCOUNT + "/create",
                HttpMethod.POST,
                new HttpEntity<>(createAccountIvanov, getHeaders()),
                ACCOUNT_TYPE_REFERENCE
        );

        assertThat(createdAccountIvanov.getBody()).isNotNull();
        var accountId = createdAccountIvanov.getBody().getId();
        assertThat(accountId).isNotNull();

        var createdAccountNotIvanov = restTemplate.exchange(
                ACCOUNT + "/create",
                HttpMethod.POST,
                new HttpEntity<>(createAccountNotIvanov, getHeaders()),
                ACCOUNT_TYPE_REFERENCE
        );

        assertThat(createdAccountNotIvanov.getBody()).isNotNull();
        var accountId2 = createdAccountNotIvanov.getBody().getId();
        assertThat(accountId2).isNotNull();

        var accountNumberIvanov = createdAccountIvanov.getBody().getAccountNumber();
        var accountNumberNotIvanov = createdAccountNotIvanov.getBody().getAccountNumber();

        var depositWithdrawAccountDto = new DepositWithdrawAccountDto()
                .setAccountNumber(accountNumberNotIvanov)
                .setSumm(50.00);

        var depositedAccount = restTemplate.exchange(
                ACCOUNT + "/deposit",
                HttpMethod.POST,
                new HttpEntity<>(depositWithdrawAccountDto, getHeaders()),
                ACCOUNT_TYPE_REFERENCE
        );

        assertThat(depositedAccount.getBody()).isNotNull();
        var balance = depositedAccount.getBody().getBalance();
        assertThat(balance).isEqualTo(50.00);

        return Pair.of(createdAccountIvanov.getBody(), createdAccountNotIvanov.getBody());
    }

    private ClientDto getClientDto(String firstName, String lastName, String surName, String inn, Long id) {
        return new ClientDto()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setSurName(surName)
                .setInn(inn)
                .setId(id);
    }

    private DictionaryBankDto getDictionaryBankDto() {
        var dictionaryBankDto = new DictionaryBankDto()
                .setAddress("Россия г.Рязань, ул.Букашкина 7")
                .setBic(new Random().ints(9, 0, 9)
                        .mapToObj(Integer::toString).collect(Collectors.joining()))
                .setName("Омега Банк");
        return dictionaryBankDto;
    }
}
