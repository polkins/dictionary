package com.example.dictionary.repository;

import com.example.dictionary.api.dto.DictionaryAccountDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Repository
public class MyJDBCRepo {
    private final String _url;
    private final String _username;
    private final String _password;

    public MyJDBCRepo(@Value("${spring.datasource.url}") String url,
                      @Value("${spring.datasource.username}") String username,
                      @Value("${spring.datasource.password}") String password) {

        _url = url;
        _username = username;
        _password = password;
    }

    public ArrayList<DictionaryAccountDto> getAccountsByBankIdWithJDBC(Long id) {
        var accounts = new ArrayList<DictionaryAccountDto>();
        try {
            try (Connection connection = DriverManager.getConnection(_url, _username, _password);
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accounts WHERE bank_id = ?")) {
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    DictionaryAccountDto accountDto = new DictionaryAccountDto();
                    accountDto.setId(resultSet.getLong("id"));
                    accountDto.setBalance(resultSet.getDouble("balance"));
                    accountDto.setAccountNumber(resultSet.getString("account_number"));
                    accountDto.setBankId(resultSet.getLong("bank_id"));
                    accountDto.setClientId(resultSet.getLong("client_id"));

                    accounts.add(accountDto);
                }
            }
        } catch (Exception ex) {
            System.out.println("failed...");
            System.out.println(ex);
        }

        return accounts;
    }

    @PostConstruct
    private void loadPostgresqlDriver() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
    }
}