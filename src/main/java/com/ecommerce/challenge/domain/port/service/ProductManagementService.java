package com.ecommerce.challenge.domain.port.service;

import com.ecommerce.challenge.domain.exception.EcommerceException;
import com.ecommerce.challenge.domain.model.Product;

import java.util.List;

public interface ProductManagementService {
    List<Product> getAll();

    Product save(Product paymentRuleModel);

    Product findById(Long id) throws EcommerceException;

    Product update(Long id, Product model) throws EcommerceException;

    Void deleteById(Long id);

}
