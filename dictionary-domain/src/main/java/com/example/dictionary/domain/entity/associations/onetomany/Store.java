package com.example.dictionary.domain.entity.associations.onetomany;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "stores")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "store")
    private List<Product> products;
}
