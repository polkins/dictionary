package com.example.dictionary.domain.entity.associations.onetoone;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;

    private String manufacturer;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "engine_id")
    private Engine engine;
}
