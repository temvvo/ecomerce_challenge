package com.ecommerce.challenge.infrastructure.adapter.db.mapper;

import com.ecommerce.challenge.domain.model.Product;
import com.ecommerce.challenge.infrastructure.adapter.db.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ProductEntityMapper {

    @Mapping(target = "id", ignore = true)
    ProductEntity toEntity(Product model);

    Product convert(ProductEntity entity);


    List<Product> convert(List<ProductEntity> all);

}
