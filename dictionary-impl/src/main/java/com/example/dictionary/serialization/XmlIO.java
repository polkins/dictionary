package com.example.dictionary.serialization;

import com.example.dictionary.api.dto.EmployeeDto;

import java.util.Optional;

public interface XmlIO {
    void write(EmployeeDto employeeDto, String path);

    Optional<EmployeeDto> read(String path);
}
