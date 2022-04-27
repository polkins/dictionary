package com.example.dictionary.domain.entity.associations.onetoone;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "cars")
@EqualsAndHashCode(of = "id")
public class Car {
    @Id
    @GenericGenerator(
            name = "ID_GENERATOR_CARS",
            strategy = "enhanced-sequence",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "cars_sequence")
            })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR_CARS")
    private Long id;

    private String model;

    private String manufacturer;

    @OneToOne(optional = false)
    @JoinColumn(name = "engine_id")
    private Engine engine;
}
