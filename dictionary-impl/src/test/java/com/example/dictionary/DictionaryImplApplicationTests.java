package com.example.dictionary;

import com.example.dictionary.common.AbstractIntegrationTest;
import com.example.dictionary.query.QueryParam;
import com.example.dictionary.query.QueryStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;

import static com.example.dictionary.ParametrizedTypeReferenceHolder.DICTIONARY_BANK_TYPE_REFERENCE;
import static com.example.dictionary.utils.DictionaryUtils.API_PREFIX;
import static com.example.dictionary.utils.DictionaryUtils.DICTIONARY;

public class DictionaryImplApplicationTests extends AbstractIntegrationTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Test
    public void getEntity() {

        HttpEntity<?> request = new HttpEntity<>(getHeaders());

        var ss =  restTemplate.exchange(
                        API_PREFIX + DICTIONARY + "/{id}",
                        HttpMethod.GET,
                        request,
                        DICTIONARY_BANK_TYPE_REFERENCE
                );
    }
}
