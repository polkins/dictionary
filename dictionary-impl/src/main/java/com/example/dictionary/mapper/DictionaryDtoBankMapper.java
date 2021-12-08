package com.example.dictionary.mapper;

import com.example.dictionary.api.dto.DictionaryBankDto;
import com.example.dictionary.domain.entity.bank.Bank;
import org.mapstruct.Mapper;

@Mapper
public interface DictionaryDtoBankMapper extends AbstractMapper<Bank, DictionaryBankDto> {
}
