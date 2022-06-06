package com.example.dictionary.serialization;

import com.example.dictionary.api.dto.EmployeeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JsonIOImpl implements JsonIO {

    @Override
    public Optional<String> write(EmployeeDto employeeDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            var json = objectMapper.writeValueAsString(employeeDto);
            return Optional.of(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<EmployeeDto> read(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            var fromJson = objectMapper.readValue(json, EmployeeDto.class);
            return Optional.of(fromJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
