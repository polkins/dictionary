package com.example.dictionary.domain.entity.bank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Accessors(chain = true)
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "banks")
public class Bank {

    @Id
    @GenericGenerator(
            name = "ID_GENERATOR_BANKS",
            strategy = "enhanced-sequence",
            parameters = {
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "sequence_name", value = "banks_sequence")
            })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR_BANKS")
    private Long id;

    private String name;

    private String address;

    private String bic;
}
