package com.example.dictionary.domain.entity.account;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Accessors(chain = true)
@Entity(name = "accounts")
@EqualsAndHashCode(of = "id")
public class Account {

    @Id
    @GenericGenerator(
            name = "ID_GENERATOR_ACCOUNTS",
            strategy = "enhanced-sequence",
            parameters = {
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "sequence_name", value = "accounts_sequence")
            })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR_ACCOUNTS")
    private Long id;

    private String accountNumber;

    private Long clientId;

    private Long bankId;

    private Double balance;
}
