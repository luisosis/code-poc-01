package org.poc01.product.infrastructure.inbound.rest;

import org.poc01.product.domain.model.Product;

public class ProductResponseMapper {

    private ProductResponseMapper() {
    }

    public static ProductResponse toResponse(Product product) {
        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setActive(product.getActive());

        return response;
    }
}
