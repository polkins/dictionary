package com.example.dictionary;

import com.example.dictionary.api.dto.DictionaryBankDto;
import com.example.dictionary.common.AbstractIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;

import static com.example.dictionary.ParametrizedTypeReferenceHolder.DICTIONARY_BANK_TYPE_REFERENCE;
import static com.example.dictionary.ParametrizedTypeReferenceHolder.ID_TYPE_REFERENCE;
import static com.example.dictionary.utils.DictionaryUtils.API_PREFIX;
import static com.example.dictionary.utils.DictionaryUtils.DICTIONARY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DictionaryImplApplicationTests extends AbstractIntegrationTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Test
    public void createAndGetEntity() {
        DictionaryBankDto dictionaryBankDto = new DictionaryBankDto()
                .setAddress("Россия г.Рязань, ул.Букашкина 7")
                .setBic("044525733")
                .setName("Омега Банк");

        HttpEntity<?> request = new HttpEntity<>(dictionaryBankDto, getHeaders());

        ResponseEntity<Long> createdBank = restTemplate.exchange(
                API_PREFIX + DICTIONARY + "/create",
                HttpMethod.POST,
                request,
                ID_TYPE_REFERENCE
        );

        assertThat(createdBank.getBody()).isNotNull();
        Long id = createdBank.getBody();
        assertThat(id).isNotNull();

        HttpEntity<?> request2 = new HttpEntity<>(getHeaders());

        var selectedBank = restTemplate.exchange(
                API_PREFIX + DICTIONARY + "/{id}",
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
}
