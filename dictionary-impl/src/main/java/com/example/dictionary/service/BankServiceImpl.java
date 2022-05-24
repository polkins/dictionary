package com.example.dictionary.service;

import com.example.dictionary.annotation.Audit;
import com.example.dictionary.api.dto.DictionaryBankDto;
import com.example.dictionary.domain.entity.bank.Bank;
import com.example.dictionary.mapper.DictionaryDtoBankMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class BankServiceImpl implements BankService {
    @Lazy
    @Autowired
    private BankService bankService;

    @Autowired
    private CrudRepository<Bank, Long> bankRepository;

    @Autowired
    private DictionaryDtoBankMapper mapper;

    @Override
    public Long createBank(DictionaryBankDto dictionaryBankDto) {
        return bankRepository.save(mapper.toDomainModel(dictionaryBankDto)).getId();
    }

    @Audit
    @Override
    public DictionaryBankDto getBank(Long id) {
        return bankRepository.findById(id)
                .map(mapper::toDto)
                .orElse(new DictionaryBankDto());
    }

    // Если вызвать getBank напрямую через this.getBank(), то будет вызываться метод самого экземпляра,
    // а не proxy, proxy ничего не знает об этом вызове и поэтому audit вызовется только один раз для getBank1(),
    // поэтому используется костыль с внедренным экземпляром (bankService), который как раз и будет proxy,
    // в этом случае audit вызовется два раза, т.е. для getBank1() и для getBank()
    @Audit
    public DictionaryBankDto getBank1(Long id){
        return bankService.getBank(id);
    }
}
