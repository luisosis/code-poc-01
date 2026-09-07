package org.poc01.product.infrastructure.inbound.rest;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean active;
}
