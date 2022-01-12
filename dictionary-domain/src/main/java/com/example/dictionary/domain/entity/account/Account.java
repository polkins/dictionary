package com.example.dictionary.domain.entity.account;

import com.example.dictionary.domain.entity.bank.Bank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "accounts")
@EqualsAndHashCode(of = "id")
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

    @JoinColumn(name = "bank_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Bank bank;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status")
    private StatusAccount accountStatus;

    @Column(name = "account_number")
    private Long accountNumber;

    @Column(name = "client_id")
    private Long clientId;
}
