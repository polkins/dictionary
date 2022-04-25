package com.example.dictionary.mapper.qualifier;

import org.springframework.stereotype.Component;

@Component
@EmployeeHandlerQualifier
public class EmployeeHandler {
    @ChangeDescriptionQualifier
    public String ChangeDescription(String description) {
        return "***" + description + "***";
    }
}
