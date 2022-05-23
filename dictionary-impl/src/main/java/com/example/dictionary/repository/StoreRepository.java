package com.example.dictionary.repository;

import com.example.dictionary.domain.entity.associations.onetomany.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
}
