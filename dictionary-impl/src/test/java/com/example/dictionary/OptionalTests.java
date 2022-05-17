package com.example.dictionary;

import com.example.dictionary.api.dto.DictionaryAccountDto;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OptionalTests {
    @Test
    public void orElse() {
        Optional<DictionaryAccountDto> accountDtoOptional = Optional.empty();

        var otherAccountDto = new DictionaryAccountDto(1L, "666", 1L, 1L, 100.0);

        var result = accountDtoOptional.orElse(otherAccountDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(otherAccountDto.getId());
        assertThat(result.getClientId()).isEqualTo(otherAccountDto.getClientId());
        assertThat(result.getBankId()).isEqualTo(otherAccountDto.getBankId());
        assertThat(result.getBalance()).isEqualTo(otherAccountDto.getBalance());
        assertThat(result.getAccountNumber()).isEqualTo(otherAccountDto.getAccountNumber());
    }

    @Test
    public void orElseGet() {
        Optional<DictionaryAccountDto> accountDtoOptional = Optional.empty();

        var result = accountDtoOptional.orElseGet(DictionaryAccountDto::new);

        assertThat(result).isNotNull();
    }

    @Test
    public void map() {
        var accountDto = new DictionaryAccountDto();
        accountDto.setId(1L);
        accountDto.setClientId(1L);
        accountDto.setBankId(1L);
        accountDto.setBalance(100.0);
        accountDto.setAccountNumber("666");

        Optional<DictionaryAccountDto> accountDtoOptional = Optional.of(accountDto);

        var result = accountDtoOptional
                .map(DictionaryAccountDto::getAccountNumber)
                .orElseGet(String::new);

        assertThat(result).isEqualTo(accountDto.getAccountNumber());
    }

    @Test
    public void flatMap() {
        var accountDto = new DictionaryAccountDto(1L, "666", 1L, 1L, 100.0);
        Optional<DictionaryAccountDto> accountDtoOptional = Optional.of(accountDto);

        var result = Optional.of(accountDtoOptional)
                .flatMap(i-> Optional.of(i.get().getAccountNumber()))
                .orElse("");

        assertThat(result).isEqualTo(accountDto.getAccountNumber());
    }
}
