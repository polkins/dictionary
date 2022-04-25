package com.example.dictionary.mapper;

import com.example.dictionary.api.dto.EmployeeDto;
import com.example.dictionary.domain.entity.employee.Employee;
import com.example.dictionary.mapper.qualifier.ChangeDescriptionQualifier;
import com.example.dictionary.mapper.qualifier.EmployeeHandler;
import com.example.dictionary.mapper.qualifier.EmployeeHandlerQualifier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = {DictionaryDtoBankMapper.class, EmployeeHandler.class})
public interface EmployeeMapper {
    @Mapping(source = "firstName", target = "name")
    @Mapping(source = "lastName", target = "surname")
    EmployeeDto toDto(Employee employee);

    @Mapping(target = "description", qualifiedBy = {EmployeeHandlerQualifier.class, ChangeDescriptionQualifier.class})
    @Mapping(source = "name", target = "firstName")
    @Mapping(source = "surname", target = "lastName")
    @Mapping(target = "id", ignore = true)
    Employee toModel(EmployeeDto employeeDto);

    @Mapping(source = "firstName", target = "name")
    @Mapping(source = "lastName", target = "surname")
    List<EmployeeDto> toDto(List<Employee> employeeList);

    @Mapping(source = "name", target = "firstName")
    @Mapping(source = "surname", target = "lastName")
    @Mapping(target = "id", ignore = true)
    List<Employee> toModel(List<EmployeeDto> employeeDtoList);
}
