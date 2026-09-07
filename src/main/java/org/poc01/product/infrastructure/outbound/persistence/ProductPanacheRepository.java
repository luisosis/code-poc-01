package org.poc01.product.infrastructure.outbound.persistence;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductPanacheRepository implements PanacheRepository<ProductEntity> {
}
