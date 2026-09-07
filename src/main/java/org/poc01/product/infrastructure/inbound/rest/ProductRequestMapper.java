package org.poc01.product.infrastructure.inbound.rest;

import org.poc01.product.domain.model.Product;

public class ProductRequestMapper {

    private ProductRequestMapper() {
    }

    public static Product toDomain(ProductRequest request) {
        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setActive(request.getActive());

        return product;
    }
}
