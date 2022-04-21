package com.example.dictionary.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {
    public Long id;
    public String name;
    public String surname;
    public String type;
    public DictionaryBankDto bank;
}
