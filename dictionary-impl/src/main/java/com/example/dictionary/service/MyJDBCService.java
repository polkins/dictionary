package com.example.dictionary.service;

import com.example.dictionary.api.dto.DictionaryAccountDto;

import java.util.ArrayList;

public interface MyJDBCService {
    ArrayList<DictionaryAccountDto> getByIdWithJDBC(Long id);
}
