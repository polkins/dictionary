package com.example.dictionary.domain.entity.associations.embeddable;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "parcels")
public class Parcel {
    @Id
    private Long id;

    private Double size;

    private String type;

    @Embedded
    private Address address;
}
