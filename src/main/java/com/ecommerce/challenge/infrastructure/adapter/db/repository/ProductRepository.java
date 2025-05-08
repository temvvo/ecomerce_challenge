package com.ecommerce.challenge.infrastructure.adapter.db.repository;

import com.ecommerce.challenge.infrastructure.adapter.db.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAll();


}
