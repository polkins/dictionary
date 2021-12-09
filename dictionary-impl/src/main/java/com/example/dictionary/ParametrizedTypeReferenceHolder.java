package com.example.dictionary;

import com.example.dictionary.api.dto.DictionaryBankDto;
import org.springframework.core.ParameterizedTypeReference;

@SuppressWarnings("Convert2Diamond")
public class ParametrizedTypeReferenceHolder {

    public static final ParameterizedTypeReference<DictionaryBankDto> DICTIONARY_BANK_TYPE_REFERENCE = new
            ParameterizedTypeReference<>() {
            };

    public static final ParameterizedTypeReference<Long> ID_TYPE_REFERENCE = new
            ParameterizedTypeReference<>() {
            };
}
