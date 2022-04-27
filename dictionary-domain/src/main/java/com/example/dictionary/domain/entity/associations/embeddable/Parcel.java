package com.example.dictionary.domain.entity.associations.embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "parcels")
@EqualsAndHashCode(of = "id")
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double size;

    private String type;

    @Embedded
    private Address address;
}
