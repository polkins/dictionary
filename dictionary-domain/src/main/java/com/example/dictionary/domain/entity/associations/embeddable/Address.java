package com.example.dictionary.domain.entity.associations.embeddable;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class Address {
    @Column(nullable = false)
    private String street;

    @Column(nullable = false, length = 5)
    private String zipcode;

    @Column(nullable = false)
    private String city;

    public Address() {
    }

    public Address(String street, String zipcode, String city) {
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
    }
}
