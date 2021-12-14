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
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "accounts")
public class Account {

    @Id
    @GenericGenerator(
            name = "ID_GENERATOR",
            strategy = "enhanced-sequence",
            parameters = {
                    @Parameter(name = "initial_value", value = "0"),
                    @Parameter(name = "sequence_name", value = "accounts_sequence")
            })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @Enumerated(EnumType.STRING)
    private StatusAccount accountStatus;

    private Long accountNumber;

    private Long clientId;
}
