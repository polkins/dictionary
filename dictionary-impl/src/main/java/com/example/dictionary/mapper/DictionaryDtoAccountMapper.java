package com.example.dictionary.mapper;

import com.example.dictionary.api.dto.DictionaryAccountDto;
import com.example.dictionary.domain.entity.account.Account;
import org.mapstruct.Mapper;

@Mapper
public interface DictionaryDtoAccountMapper extends AbstractMapper<Account, DictionaryAccountDto>{
}
