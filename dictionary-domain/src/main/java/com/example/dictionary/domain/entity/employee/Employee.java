package com.example.dictionary.domain.entity.employee;

import com.example.dictionary.domain.entity.bank.Bank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "first_name")
    public String firstName;

    @Column(name = "last_name")
    public String lastName;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    public EmployeeType type;

    @OneToOne
    @JoinColumn(name = "bank_id")
    public Bank bank;

    @Column(name = "description")
    public String description;
}
