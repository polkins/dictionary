package com.example.dictionary;

import com.example.dictionary.api.dto.DictionaryBankDto;
import com.example.dictionary.domain.entity.bank.Bank;
import com.example.dictionary.mapper.DictionaryDtoBankMapperImpl;
import com.example.dictionary.service.BankServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.repository.CrudRepository;

import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class MockitoTests {
    @InjectMocks
    private BankServiceImpl service;

    @Mock
    private CrudRepository<Bank, Long> bankRepositoryMock;

    @Spy
    private DictionaryDtoBankMapperImpl mapperSpy;

    @Test
    public void createBankWithMockAndArgumentCaptor() {
        var bank = new Bank();
        bank.setName("ЧертБанк");
        bank.setBic("666");
        bank.setAddress("г. Ад, ул. Дьявольская");

        var createdBank = new Bank();
        createdBank.setId(2L);
        createdBank.setName(bank.getName());
        createdBank.setBic(bank.getBic());
        createdBank.setAddress(bank.getAddress());

        var dictionaryBankDto = new DictionaryBankDto(bank.getId(), bank.getName(), bank.getAddress(), bank.getBic());

//        doReturn(createdBank).when(bankRepository.save(bank));
        when(bankRepositoryMock.save(bank)).thenReturn(createdBank);

        var result = service.createBank(dictionaryBankDto);

        assertThat(result).isEqualTo(createdBank.getId());

        ArgumentCaptor<Bank> captor = ArgumentCaptor.forClass(Bank.class);
        verify(bankRepositoryMock, times(1)).save(captor.capture());
        assertThat(captor.getAllValues().size()).isEqualTo(1);
        var capturedArgument = captor.getValue();
        assertThat(capturedArgument.getId()).isNull();
        assertThat(capturedArgument.getName()).isEqualTo(bank.getName());
        assertThat(capturedArgument.getAddress()).isEqualTo(bank.getAddress());
        assertThat(capturedArgument.getBic()).isEqualTo(bank.getBic());
    }

    @Test
    public void getBankWithMockAndSpy() {
        var bank = new Bank();
        bank.setId(1L);
        bank.setName("ЧертБанк");
        bank.setBic("666");
        bank.setAddress("г. Ад, ул. Дьявольская");

        var dictionaryBankDto = new DictionaryBankDto(bank.getId(), bank.getName(), bank.getAddress(), bank.getBic());

        when(bankRepositoryMock.findById(1L)).thenReturn(Optional.of(bank));

        when(mapperSpy.toDto(bank)).thenReturn(dictionaryBankDto);

        var result = service.getBank(1L);

        assertThat(result.getId()).isEqualTo(dictionaryBankDto.getId());
        assertThat(result.getName()).isEqualTo(dictionaryBankDto.getName());
        assertThat(result.getAddress()).isEqualTo(dictionaryBankDto.getAddress());
        assertThat(result.getBic()).isEqualTo(dictionaryBankDto.getBic());
        verify(bankRepositoryMock, times(1)).findById(1L);
        verify(mapperSpy, times(1)).toDto(bank);
    }

    @Test
    public void getBankWithAnswer() {
        final Long bankId1 = 1L;
        final Long bankId2 = 2L;
        when(bankRepositoryMock.findById(Mockito.any()))
                .thenAnswer(invocation -> {
                    var id = (Long) invocation.getArgument(0);
                    if (Objects.equals(id, bankId1)) {
                        var bank1 = new Bank();
                        bank1.setId(bankId1);
                        return Optional.of(bank1);
                    } else if (Objects.equals(id, bankId2)) {
                        var bank2 = new Bank();
                        bank2.setId(bankId2);
                        return Optional.of(bank2);
                    }
                    return Optional.empty();
                });

        var result1 = service.getBank(bankId1);
        assertThat(result1).isNotNull();
        assertThat(result1.getId()).isEqualTo(bankId1);

        var result2 = service.getBank(bankId2);
        assertThat(result2).isNotNull();
        assertThat(result2.getId()).isEqualTo(bankId2);

        verify(bankRepositoryMock, times(2)).findById(Mockito.any());
    }
}