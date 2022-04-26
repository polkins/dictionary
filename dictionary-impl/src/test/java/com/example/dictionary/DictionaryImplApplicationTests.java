package com.example.dictionary;

import com.example.dictionary.api.dto.*;
import com.example.dictionary.common.AbstractIntegrationTest;
import com.example.dictionary.domain.entity.associations.onetomany.Product;
import com.example.dictionary.domain.entity.associations.onetomany.Store;
import com.example.dictionary.domain.entity.associations.onetoone.Car;
import com.example.dictionary.domain.entity.associations.onetoone.Engine;
import com.example.dictionary.domain.entity.bank.Bank;
import com.example.dictionary.domain.entity.employee.Employee;
import com.example.dictionary.domain.entity.employee.EmployeeType;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;
import ru.nonsense.auth.client.api.dto.ClientDto;
import ru.nonsense.auth.client.feign.AuthControllerFeign;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.example.dictionary.ParametrizedTypeReferenceHolder.*;
import static com.example.dictionary.utils.DictionaryUtils.ACCOUNT;
import static com.example.dictionary.utils.DictionaryUtils.BANK;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

public class DictionaryImplApplicationTests extends AbstractIntegrationTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    private AuthControllerFeign authControllerFeign;

    @Test
    public void createAndGetEntity() {
        DictionaryBankDto dictionaryBankDto = getDictionaryBankDto();

        HttpEntity<?> request = new HttpEntity<>(dictionaryBankDto, getHeaders());

        ResponseEntity<Long> createdBank = restTemplate.exchange(
                BANK + "/create",
                HttpMethod.POST,
                request,
                ID_TYPE_REFERENCE
        );

        assertThat(createdBank.getBody()).isNotNull();
        Long id = createdBank.getBody();
        assertThat(id).isNotNull();

        HttpEntity<?> request2 = new HttpEntity<>(getHeaders());

        var selectedBank = restTemplate.exchange(
                BANK + "/{id}",
                HttpMethod.GET,
                request2,
                DICTIONARY_BANK_TYPE_REFERENCE,
                id
        );

        DictionaryBankDto bankBody = selectedBank.getBody();
        assertThat(bankBody).isNotNull();
        assertThat(bankBody.getAddress()).isEqualTo(dictionaryBankDto.getAddress());
        assertThat(bankBody.getBic()).isEqualTo(dictionaryBankDto.getBic());
        assertThat(bankBody.getName()).isEqualTo(dictionaryBankDto.getName());
    }

    @Test
    public void transferMoneyFromOneAccountToAnother() {
        var accounts = createAccountsAndBank();

        var accountNumberIvanov = accounts.getLeft().getAccountNumber();
        var accountNumberNotIvanov = accounts.getRight().getAccountNumber();

        var transferAccountDto = new TransferAccountDto()
                .setWithdrawFromAccountNumber(accountNumberNotIvanov)
                .setDepositIntoAccountNumber(accountNumberIvanov)
                .setSumm(20.00);

        var transferResponse = restTemplate.exchange(
                ACCOUNT + "/transfer",
                HttpMethod.POST,
                new HttpEntity<>(transferAccountDto, getHeaders()),
                ACCOUNT_TYPE_REFERENCE
        );

        assertThat(transferResponse.getBody()).isNotNull();
        var balanceIvanov = transferResponse.getBody().getBalance();
        assertThat(balanceIvanov).isEqualTo(20.00);

        var notIvanov = restTemplate.exchange(
                ACCOUNT + "/{id}",
                HttpMethod.GET,
                new HttpEntity<>(getHeaders()),
                ACCOUNT_TYPE_REFERENCE,
                accounts.getRight().getId()
        );

        assertThat(notIvanov.getBody()).isNotNull();
        var balanceNotIvanov = notIvanov.getBody().getBalance();
        assertThat(balanceNotIvanov).isEqualTo(30.00);
    }

    @Test
    public void getByIdWithJDBC(){
        var accounts = createAccountsAndBank();

        var accountNumberIvanov = accounts.getLeft();
        var accountNumberNotIvanov = accounts.getRight();

        var bankId = accountNumberIvanov.getBankId();

        var jdbcAccounts = myJDBCService.getByIdWithJDBC(bankId);

        assertThat(jdbcAccounts.size()).isEqualTo(2);

        assertThat(jdbcAccounts.get(0).getId()).isEqualTo(accountNumberIvanov.getId());
        assertThat(jdbcAccounts.get(0).getAccountNumber()).isEqualTo(accountNumberIvanov.getAccountNumber());
        assertThat(jdbcAccounts.get(0).getBankId()).isEqualTo(accountNumberIvanov.getBankId());
        assertThat(jdbcAccounts.get(0).getClientId()).isEqualTo(accountNumberIvanov.getClientId());

        assertThat(jdbcAccounts.get(1).getId()).isEqualTo(accountNumberNotIvanov.getId());
        assertThat(jdbcAccounts.get(1).getAccountNumber()).isEqualTo(accountNumberNotIvanov.getAccountNumber());
        assertThat(jdbcAccounts.get(1).getBankId()).isEqualTo(accountNumberNotIvanov.getBankId());
        assertThat(jdbcAccounts.get(1).getClientId()).isEqualTo(accountNumberNotIvanov.getClientId());
    }

    @Test
    public void mapEmployeeToDto(){
        var bank = new Bank();
        bank.setId(1L);
        bank.setName("БаБанк");
        bank.setBic("5555");

        var employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Иван");
        employee.setLastName("Иванов");
        employee.setType(EmployeeType.ECONOMIST);
        employee.setBank(bank);

        var employeeDto = employeeMapper.toDto(employee);

        assertThat(employeeDto.getName()).isEqualTo(employee.getFirstName());
        assertThat(employeeDto.getSurname()).isEqualTo(employee.getLastName());
        assertThat(employeeDto.getType()).isEqualTo(employee.getType().toString());

        assertThat(employeeDto.getBank()).isNotNull();
        assertThat(employeeDto.getBank().getId()).isEqualTo(bank.getId());
        assertThat(employeeDto.getBank().getName()).isEqualTo(bank.getName());
        assertThat(employeeDto.getBank().getBic()).isEqualTo(bank.getBic());
    }

    @Test
    public void mapEmployeeDtoToModel(){
        var bankDto = new DictionaryBankDto();
        bankDto.setId(1L);
        bankDto.setName("БаБанк");
        bankDto.setBic("5555");

        final String description = "xxx-xxx";
        var employeeDto = new EmployeeDto();
        employeeDto.setName("Иван");
        employeeDto.setSurname("Иванов");
        employeeDto.setType(EmployeeType.MANAGER.toString());
        employeeDto.setBank(bankDto);
        employeeDto.setDescription(description);

        var employee = employeeMapper.toModel(employeeDto);

        assertThat(employee.getId()).isNull();
        assertThat(employee.getFirstName()).isEqualTo(employeeDto.getName());
        assertThat(employee.getLastName()).isEqualTo(employeeDto.getSurname());
        assertThat(employee.getType()).isEqualTo(EmployeeType.MANAGER);
        assertThat(employee.getDescription()).isEqualTo(employeeHandler.ChangeDescription(description));

        assertThat(employee.getBank()).isNotNull();
        assertThat(employee.getBank().getId()).isEqualTo(bankDto.getId());
        assertThat(employee.getBank().getName()).isEqualTo(bankDto.getName());
        assertThat(employee.getBank().getBic()).isEqualTo(bankDto.getBic());
    }

    @Test
    public void mapEmployeeToDtoList(){
        var bank = new Bank();
        bank.setId(1L);
        bank.setName("БаБанк");
        bank.setBic("5555");

        var employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Иван");
        employee.setLastName("Иванов");
        employee.setType(EmployeeType.ECONOMIST);
        employee.setBank(bank);

        var employeeList = new ArrayList<Employee>();
        employeeList.add(employee);

        var employeeDtoList = employeeMapper.toDto(employeeList);

        assertThat(employeeDtoList.size()).isEqualTo(1);

        assertThat(employeeDtoList.get(0).getName()).isEqualTo(employee.getFirstName());
        assertThat(employeeDtoList.get(0).getSurname()).isEqualTo(employee.getLastName());
        assertThat(employeeDtoList.get(0).getType()).isEqualTo(employee.getType().toString());

        assertThat(employeeDtoList.get(0).getBank()).isNotNull();
        assertThat(employeeDtoList.get(0).getBank().getId()).isEqualTo(bank.getId());
        assertThat(employeeDtoList.get(0).getBank().getName()).isEqualTo(bank.getName());
        assertThat(employeeDtoList.get(0).getBank().getBic()).isEqualTo(bank.getBic());
    }

    @Test
    public void mapEmployeeDtoToModelList(){
        var bankDto = new DictionaryBankDto();
        bankDto.setId(1L);
        bankDto.setName("БаБанк");
        bankDto.setBic("5555");

        var employeeDto = new EmployeeDto();
        employeeDto.setName("Иван");
        employeeDto.setSurname("Иванов");
        employeeDto.setType(EmployeeType.MANAGER.toString());
        employeeDto.setBank(bankDto);

        var employeeDtoList = new ArrayList<EmployeeDto>();
        employeeDtoList.add(employeeDto);

        var employeeList = employeeMapper.toModel(employeeDtoList);

        assertThat(employeeDtoList.size()).isEqualTo(1);

        assertThat(employeeList.get(0).getId()).isNull();
        assertThat(employeeList.get(0).getFirstName()).isEqualTo(employeeDto.getName());
        assertThat(employeeList.get(0).getLastName()).isEqualTo(employeeDto.getSurname());
        assertThat(employeeList.get(0).getType()).isEqualTo(EmployeeType.MANAGER);

        assertThat(employeeList.get(0).getBank()).isNotNull();
        assertThat(employeeList.get(0).getBank().getId()).isEqualTo(bankDto.getId());
        assertThat(employeeList.get(0).getBank().getName()).isEqualTo(bankDto.getName());
        assertThat(employeeList.get(0).getBank().getBic()).isEqualTo(bankDto.getBic());
    }

    @Test
    public void createEngineAndCar_OneToOne(){
        var engine = new Engine();
        engine.setPower(123L);

        var createdEngine = engineRepository.save(engine);

        assertThat(createdEngine).isNotNull();
        assertThat(createdEngine.getId()).isNotNull();
        assertThat(createdEngine.getPower()).isEqualTo(engine.getPower());

        var car = new Car();
        car.setManufacturer("Kia Motors");
        car.setModel("Rio Style");
        car.setEngine(createdEngine);

        var createdCar = carRepository.save(car);

        assertThat(createdCar).isNotNull();
        assertThat(createdCar.getId()).isNotNull();
        assertThat(createdCar.getManufacturer()).isEqualTo(car.getManufacturer());
        assertThat(createdCar.getModel()).isEqualTo(car.getModel());
        assertThat(createdCar.getEngine()).isNotNull();
        assertThat(createdEngine.getId()).isNotNull();
        assertThat(createdEngine.getPower()).isEqualTo(engine.getPower());
    }

    @Test
    public void createProductsAndStore_OneToMany_ManyToOne(){
        var product = new Product();
        product.setName("Молоко для избранных котиков");
        product.setPrice(1450.0);

        var createdProduct = productRepository.save(product);

        assertThat(createdProduct).isNotNull();
        assertThat(createdProduct.getId()).isNotNull();
        assertThat(createdProduct.getName()).isEqualTo(product.getName());
        assertThat(createdProduct.getPrice()).isEqualTo(product.getPrice());

        var store = new Store();
        store.setName("5");
        store.setAddress("г.Продуктовый, ул. Молочная, д. 23");
        store.setProducts(List.of(product));

        var createdStore = storeRepository.save(store);

        assertThat(createdStore).isNotNull();
        assertThat(createdStore.getId()).isNotNull();
        assertThat(createdStore.getName()).isEqualTo(store.getName());
        assertThat(createdStore.getAddress()).isEqualTo(store.getAddress());
    }

    private Pair<DictionaryAccountDto, DictionaryAccountDto> createAccountsAndBank() {
        DictionaryBankDto dictionaryBankDto = getDictionaryBankDto();

        var clientIvanov = getClientDto("Иванов", "Иван", "Иванович", "644901001", 1L);
        var clientNotIvanov = getClientDto("НеИванов", "НеИван", "НеИванович", "644901002", 2L);

        var createdBank = restTemplate.exchange(
                BANK + "/create",
                HttpMethod.POST,
                new HttpEntity<>(dictionaryBankDto, getHeaders()),
                ID_TYPE_REFERENCE
        );

        assertThat(createdBank.getBody()).isNotNull();
        var id = createdBank.getBody();
        assertThat(id).isNotNull();

        when(authControllerFeign.findClientByInn("auth", clientIvanov.getInn())).thenReturn(ResponseEntity.ok(clientIvanov));
        when(authControllerFeign.findClientByInn("auth", clientNotIvanov.getInn())).thenReturn(ResponseEntity.ok(clientNotIvanov));

        var createAccountIvanov = new CreateAccountDto()
                .setBic(dictionaryBankDto.getBic())
                .setInn(clientIvanov.getInn());

        var createAccountNotIvanov = new CreateAccountDto()
                .setBic(dictionaryBankDto.getBic())
                .setInn(clientNotIvanov.getInn());

        var createdAccountIvanov = restTemplate.exchange(
                ACCOUNT + "/create",
                HttpMethod.POST,
                new HttpEntity<>(createAccountIvanov, getHeaders()),
                ACCOUNT_TYPE_REFERENCE
        );

        assertThat(createdAccountIvanov.getBody()).isNotNull();
        var accountId = createdAccountIvanov.getBody().getId();
        assertThat(accountId).isNotNull();

        var createdAccountNotIvanov = restTemplate.exchange(
                ACCOUNT + "/create",
                HttpMethod.POST,
                new HttpEntity<>(createAccountNotIvanov, getHeaders()),
                ACCOUNT_TYPE_REFERENCE
        );

        assertThat(createdAccountNotIvanov.getBody()).isNotNull();
        var accountId2 = createdAccountNotIvanov.getBody().getId();
        assertThat(accountId2).isNotNull();

        var accountNumberIvanov = createdAccountIvanov.getBody().getAccountNumber();
        var accountNumberNotIvanov = createdAccountNotIvanov.getBody().getAccountNumber();

        var depositWithdrawAccountDto = new DepositWithdrawAccountDto()
                .setAccountNumber(accountNumberNotIvanov)
                .setSumm(50.00);

        var depositedAccount = restTemplate.exchange(
                ACCOUNT + "/deposit",
                HttpMethod.POST,
                new HttpEntity<>(depositWithdrawAccountDto, getHeaders()),
                ACCOUNT_TYPE_REFERENCE
        );

        assertThat(depositedAccount.getBody()).isNotNull();
        var balance = depositedAccount.getBody().getBalance();
        assertThat(balance).isEqualTo(50.00);

        return Pair.of(createdAccountIvanov.getBody(), createdAccountNotIvanov.getBody());
    }

    private ClientDto getClientDto(String firstName, String lastName, String surName, String inn, Long id) {
        return new ClientDto()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setSurName(surName)
                .setInn(inn)
                .setId(id);
    }

    private DictionaryBankDto getDictionaryBankDto() {
        var dictionaryBankDto = new DictionaryBankDto()
                .setAddress("Россия г.Рязань, ул.Букашкина 7")
                .setBic(new Random().ints(9, 0, 9)
                        .mapToObj(Integer::toString).collect(Collectors.joining()))
                .setName("Омега Банк");
        return dictionaryBankDto;
    }
}
