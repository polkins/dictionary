package com.example.dictionary.repository;

import com.example.dictionary.domain.entity.associations.onetoone.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
