package com.ecommerce.challenge.application;

import com.ecommerce.challenge.domain.exception.EcommerceException;
import com.ecommerce.challenge.domain.model.Product;
import com.ecommerce.challenge.domain.port.repository.ProductManagementRepository;
import com.ecommerce.challenge.domain.port.service.ProductManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductManagement implements ProductManagementService {

    private final ProductManagementRepository repository;

    public ProductManagement(ProductManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> getAll() {
        return repository.findAll();
    }

    @Override
    public Product save(Product paymentRuleModel) {
        return repository.save(paymentRuleModel);
    }

    @Override
    public Product findById(Long id) throws EcommerceException {
        return repository.findById(id)
                .orElseThrow(() -> new EcommerceException(417, "product does not exists"));
    }

    @Override
    public Product update(Long id, Product product) throws EcommerceException {
          return repository.update(id, product);
    }

    @Override
    public Void deleteById(Long id) {
         repository.delete(id);
        return null;
    }

}
