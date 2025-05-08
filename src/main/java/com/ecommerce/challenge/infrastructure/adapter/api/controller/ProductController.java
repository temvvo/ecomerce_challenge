package com.ecommerce.challenge.infrastructure.adapter.api.controller;


import com.ecommerce.challenge.domain.exception.EcommerceException;
import com.ecommerce.challenge.domain.model.Product;
import com.ecommerce.challenge.domain.port.service.ProductManagementService;
import com.ecommerce.challenge.infrastructure.adapter.api.domain.ProductApiModel;
import com.ecommerce.challenge.infrastructure.adapter.api.mapper.ProductApiMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RequestMapping(path = "/product", produces = APPLICATION_JSON_VALUE)

@RestController
public class ProductController {
    private final ProductManagementService service;
    private final ProductApiMapper mapper = Mappers.getMapper(ProductApiMapper.class);

    public ProductController(ProductManagementService service) {
        this.service = service;
    }

    @GetMapping(
            produces = { "application/json" }
    )

    public ResponseEntity<List<ProductApiModel>> getProducts()  {

        return new ResponseEntity<>(mapper.map(service.getAll()), HttpStatus.OK);
    }

    @DeleteMapping(
            value = "/{id}",
            produces = { "application/json" }
    )

    public ResponseEntity<Void> deleteProduct(
            @Parameter(name = "id", description = "Unique product resource identifier", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    )  {
        return new ResponseEntity<>(service.deleteById(id), HttpStatus.NO_CONTENT);

    }

    @GetMapping(
            value = "/{id}",
            produces = { "application/json" }
    )

    public ResponseEntity<ProductApiModel> getProductById(
            @Parameter(name = "id", description = "Unique product resource identifier", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    ) throws EcommerceException {
        return new ResponseEntity<>(mapper.map(service.findById(id)), HttpStatus.OK);
    }


    @PutMapping(
            value = "/{id}",
            produces = { "application/json" },
            consumes = { "application/json" }
    )

    public ResponseEntity<ProductApiModel> putProduct(
            @Parameter(name = "id", description = "Unique product resource identifier", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id,
            @Parameter(name = "Product", description = "Payload containing the product.") @Valid @RequestBody(required = true) ProductApiModel productApiModel
    ) throws EcommerceException {
        validateProductRequest(productApiModel);
        Product product = service.update(id, mapper.map(productApiModel));

        return new ResponseEntity<>(mapper.map(product), HttpStatus.OK);
    }

    private static void validateProductRequest(ProductApiModel productApiModel) throws EcommerceException {
        if(productApiModel.getCurrency() == null || productApiModel.getPrice() == null || productApiModel.getBrandName() == null || productApiModel.getDescription() == null) {
            throw new EcommerceException(417, "Product attributes can´t be null");
        }
    }


    @PostMapping(
            produces = { "application/json" },
            consumes = { "application/json" }
    )

    public ResponseEntity<ProductApiModel> postProduct(
            @Parameter(name = "Product", description = "Payload containing the product product.") @Valid @RequestBody(required = true) ProductApiModel product
    ) throws EcommerceException {
        validateProductRequest(product);

        return new ResponseEntity<>(mapper.map(service.save(mapper.map(product))), HttpStatus.CREATED);
    }

}
