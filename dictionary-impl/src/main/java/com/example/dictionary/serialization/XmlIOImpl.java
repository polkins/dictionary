package com.example.dictionary.serialization;

import com.example.dictionary.api.dto.EmployeeDto;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.Optional;

@Service
public class XmlIOImpl implements XmlIO {
    @Override
    public void write(EmployeeDto employeeDto, String path) {
        try {
            var jaxbContext = JAXBContext.newInstance(EmployeeDto.class);
            var marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            marshaller.marshal(employeeDto, new File(path));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<EmployeeDto> read(String path) {
        try {
            var jaxbContext = JAXBContext.newInstance(EmployeeDto.class);
            var unmarshal = jaxbContext.createUnmarshaller();

            var employeeDto = (EmployeeDto) unmarshal.unmarshal(new File(path));
            return Optional.of(employeeDto);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
