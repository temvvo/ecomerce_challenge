package com.ecommerce.challenge.infrastructure.adapter.api.mapper;

import com.ecommerce.challenge.domain.model.Product;
import com.ecommerce.challenge.infrastructure.adapter.api.domain.ProductApiModel;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductApiMapper {

    ProductApiModel map(Product product);
    List<ProductApiModel> map(List<Product> models);
    Product map(ProductApiModel rule);

    default Optional<Long> map(Long value) {
        return Optional.ofNullable(value);
    }

    default Long map(Optional<Long> value) {
        return value.orElse(null);
    }
}
