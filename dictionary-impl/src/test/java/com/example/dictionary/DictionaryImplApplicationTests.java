package com.example.dictionary;

import com.example.dictionary.api.dto.DictionaryAccountDto;
import com.example.dictionary.api.dto.DictionaryBankDto;
import com.example.dictionary.api.dto.StatusAccountDto;
import com.example.dictionary.common.AbstractIntegrationTest;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;

import static com.example.dictionary.ParametrizedTypeReferenceHolder.*;
import static com.example.dictionary.utils.DictionaryUtils.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DictionaryImplApplicationTests extends AbstractIntegrationTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Test
    public void create_bank_returnCorrectId() {
        DictionaryBankDto dictionaryBankDto = getBankDto();
        HttpEntity<?> request = new HttpEntity<>(dictionaryBankDto, getHeaders());

        ResponseEntity<Long> createdBank = restTemplate.exchange(
                API_PREFIX + DICTIONARY + "/create",
                HttpMethod.POST,
                request,
                ID_TYPE_REFERENCE
        );

        assertThat(createdBank.getBody())
                .isNotNull()
                .isEqualTo(4);
    }

    @Test
    public void getBank_bankId_returnCorrectBank() {
        DictionaryBankDto dictionaryBankDto = getBankDto();
        HttpEntity<?> request2 = new HttpEntity<>(getHeaders());

        var selectedBank = restTemplate.exchange(
                API_PREFIX + DICTIONARY + "/{id}",
                HttpMethod.GET,
                request2,
                DICTIONARY_BANK_TYPE_REFERENCE,
                4
        );

        DictionaryBankDto bankBody = selectedBank.getBody();
        assertThat(bankBody).isNotNull();
        assertThat(bankBody.getAddress()).isEqualTo(dictionaryBankDto.getAddress());
        assertThat(bankBody.getBic()).isEqualTo(dictionaryBankDto.getBic());
        assertThat(bankBody.getName()).isEqualTo(dictionaryBankDto.getName());
    }

    @Test
    public void create_account_returnCorrectId() {
        DictionaryAccountDto dictionaryAccountDto = getDictionaryAccountDto();
        HttpEntity<?> request = new HttpEntity<>(dictionaryAccountDto, getHeaders());

        ResponseEntity<Long> createdAccount = restTemplate.exchange(
                API_PREFIX + DICTIONARY + API_PREFIX + ACCOUNT + "/create",
                HttpMethod.POST,
                request,
                ID_TYPE_REFERENCE
        );

        assertThat(createdAccount.getBody())
                .isNotNull()
                .isEqualTo(5);
    }

    @Test
    public void getAccount_accountId_returnAccount() {
        HttpEntity<?> request2 = new HttpEntity<>(getHeaders());

        var selectedAccount = restTemplate.exchange(
                API_PREFIX + DICTIONARY + API_PREFIX + ACCOUNT + "/{id}",
                HttpMethod.GET,
                request2,
                DICTIONARY_ACCOUNT_TYPE_REFERENCE,
                5
        );

        DictionaryBankDto bankDto = getDictionaryBankDto();
        DictionaryAccountDto accountDto = selectedAccount.getBody();
        assertThat(accountDto).isNotNull();
        DictionaryAccountDto dictionaryAccountDto = getDictionaryAccountDto();
        assertThat(accountDto.getAccountStatus()).isEqualTo(dictionaryAccountDto.getAccountStatus());
        assertThat(accountDto.getAccountNumber()).isEqualTo(dictionaryAccountDto.getAccountNumber());
        assertThat(accountDto.getClientId()).isEqualTo(dictionaryAccountDto.getClientId());
        assertThat(accountDto.getBank().getId()).isEqualTo(bankDto.getId());
        assertThat(accountDto.getBank().getName()).isEqualTo(bankDto.getName());
        assertThat(accountDto.getBank().getAddress()).isEqualTo(bankDto.getAddress());
        assertThat(accountDto.getBank().getBic()).isEqualTo(bankDto.getBic());
    }

    @Nullable
    private DictionaryBankDto getDictionaryBankDto() {
        HttpEntity<?> requestForBank = new HttpEntity<>(getHeaders());
        var selectedBank = restTemplate.exchange(
                API_PREFIX + DICTIONARY + "/{id}",
                HttpMethod.GET,
                requestForBank,
                DICTIONARY_BANK_TYPE_REFERENCE,
                1
        );
        return selectedBank.getBody();
    }

    private DictionaryAccountDto getDictionaryAccountDto() {
        DictionaryBankDto bankDto = getDictionaryBankDto();
        return new DictionaryAccountDto()
                .setAccountNumber(1234567891234567891L)
                .setClientId(123L)
                .setAccountStatus(StatusAccountDto.ACTIVE)
                .setBank(bankDto);
    }

    private DictionaryBankDto getBankDto() {
        return new DictionaryBankDto()
                .setAddress("Россия г.Рязань, ул.Букашкина 7")
                .setBic("044525733")
                .setName("Омега Банк");
    }
}
