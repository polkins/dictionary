package com.example.dictionary.domain.entity.account;

import com.example.dictionary.domain.entity.bank.Bank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity(name = "Account")
@EqualsAndHashCode(of = "id")
public class Account {

    @Id
    @GenericGenerator(
            name = "ID_GENERATOR",
            strategy = "enhanced-sequence",
            parameters = {
                    @Parameter(name = "initial_value", value = "0")
            })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR")
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusAccount status;

    @OneToMany(fetch = FetchType.LAZY)
    private Bank idBank;

    private Long accountNumber;

    private Long idClient;
}
