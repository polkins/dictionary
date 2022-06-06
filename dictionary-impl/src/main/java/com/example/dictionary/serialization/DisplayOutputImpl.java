package com.example.dictionary.serialization;

import com.example.dictionary.api.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DisplayOutputImpl implements DisplayOutput {
    private final XmlIO xmlIO;
    private final JsonIO jsonIO;

    @Override
    public void display(String path) {
        var employeeDto = xmlIO.read(path);
        var json = jsonIO.write(employeeDto.orElse(new EmployeeDto()));
        System.out.println(json.orElse("failed to get json"));
    }
}
