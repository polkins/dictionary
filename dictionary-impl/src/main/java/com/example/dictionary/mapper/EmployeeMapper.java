package com.example.dictionary.mapper;

import com.example.dictionary.api.dto.EmployeeDto;
import com.example.dictionary.domain.entity.employee.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(source = "firstName", target = "name")
    @Mapping(source = "lastName", target = "surname")
    EmployeeDto toDto(Employee employee);

    @Mapping(source = "name", target = "firstName")
    @Mapping(source = "surname", target = "lastName")
    @Mapping(target = "id", ignore = true)
    Employee toModel(EmployeeDto employeeDto);
}
