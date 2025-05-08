package com.ecommerce.challenge.infrastructure.adapter.db;

import com.ecommerce.challenge.domain.exception.EcommerceException;
import com.ecommerce.challenge.domain.model.Product;
import com.ecommerce.challenge.domain.port.repository.ProductManagementRepository;
import com.ecommerce.challenge.infrastructure.adapter.db.entity.ProductEntity;
import com.ecommerce.challenge.infrastructure.adapter.db.mapper.ProductEntityMapper;
import com.ecommerce.challenge.infrastructure.adapter.db.repository.ProductRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class DbProductManagementRepository implements ProductManagementRepository {


    private final ProductRepository repository;

    private final ProductEntityMapper mapper = Mappers.getMapper(ProductEntityMapper.class);

    @Autowired
    public DbProductManagementRepository(ProductRepository productRepository) {
        this.repository = productRepository;
    }



    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id).map(mapper::convert);
    }

    @Override
    public List<Product> findAll() {
        return mapper.convert(repository.findAll());
    }

    @Override
    public Product save(Product model) {
        return mapper.convert(repository.save(mapper.toEntity(model)));
    }

    @Override
    public Product update(Long id, Product product) throws EcommerceException {
        ProductEntity entity = repository.findById(id)
                .orElseThrow(() -> new EcommerceException(417, "Product does not exists"));

        entity.setPrice(product.getPrice());
        entity.setCurrency(product.getCurrency());
        entity.setBrandName(product.getBrandName());
        entity.setDescription(product.getDescription());

        return mapper.convert(repository.save(entity));
    }


    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
