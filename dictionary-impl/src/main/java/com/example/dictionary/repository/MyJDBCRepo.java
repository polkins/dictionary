package com.example.dictionary.repository;

import com.example.dictionary.domain.entity.account.Account;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Repository
public class MyJDBCRepo {
    public ArrayList<Account> getAccountsByBankIdWithJDBC(Long id) {
        var accounts = new ArrayList<Account>();
        try {
            Class.forName("org.postgresql.Driver");

            Connection connection = DriverManager
                    .getConnection("jdbc:postgresql://postgres:5432/postgres?currentSchema=dictionary,public", "dictionary", "dictionary");

            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accounts WHERE bank_id = ?")) {
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Account user = new Account();
                    user.setId(resultSet.getLong("id"));
                    user.setBalance(resultSet.getDouble("balance"));

                    accounts.add(user);
                }
            }
        } catch (Exception ex) {
            System.out.println("failed...");
            System.out.println(ex);
        }

        return accounts;
    }
}
