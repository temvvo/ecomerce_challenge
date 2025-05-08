package com.ecommerce.challenge.infrastructure.adapter.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Optional;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductApiModel
{
    @JsonProperty("id")
    private Optional<Long> id = Optional.empty();

    @JsonProperty("brand_name")
    private String brandName;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("description")
    private String description;


}

