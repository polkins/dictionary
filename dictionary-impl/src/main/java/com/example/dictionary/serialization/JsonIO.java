package com.example.dictionary.serialization;

import com.example.dictionary.api.dto.EmployeeDto;

import java.util.Optional;

public interface JsonIO {
    Optional<String> write(EmployeeDto employeeDto);

    Optional<EmployeeDto> read(String json);
}
