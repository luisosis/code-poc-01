package org.poc01.product.infrastructure.outbound.persistence;

import org.poc01.product.domain.model.Product;

public final class ProductEntityMapper {

    private ProductEntityMapper() {
    }

    public static ProductEntity toEntity(Product product) {

        ProductEntity entity = new ProductEntity();

        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice());
        entity.setActive(product.getActive());

        return entity;
    }

    public static Product toDomain(ProductEntity entity) {

        Product product = new Product();

        product.setId(entity.getId());
        product.setName(entity.getName());
        product.setDescription(entity.getDescription());
        product.setPrice(entity.getPrice());
        product.setActive(entity.getActive());

        return product;
    }
}
