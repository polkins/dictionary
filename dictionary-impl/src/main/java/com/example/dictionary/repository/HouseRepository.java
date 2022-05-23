package com.example.dictionary.repository;

import com.example.dictionary.domain.entity.associations.manytomany.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
}
