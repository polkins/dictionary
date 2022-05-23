package com.example.dictionary.repository;

import com.example.dictionary.domain.entity.associations.onetomany.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
