package com.ecommerce.challenge.domain.port.repository;


import com.ecommerce.challenge.domain.exception.EcommerceException;
import com.ecommerce.challenge.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductManagementRepository {
    Optional<Product> findById(Long id);
    List<Product> findAll();
    Product save(Product model);
    Product update(Long id, Product product) throws EcommerceException;
    void delete(Long id);
}
