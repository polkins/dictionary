package com.example.dictionary.repository;

import com.example.dictionary.domain.entity.associations.onetoone.Engine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EngineRepository extends JpaRepository<Engine, Long> {
}
