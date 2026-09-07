package org.poc01.product.application.port.out;

import io.smallrye.mutiny.Uni;
import org.poc01.product.domain.model.Product;

import java.util.List;

public interface ProductRepository {

    Uni<List<Product>> findAll();

    Uni<Product> findById(Long id);

    Uni<Product> save(Product product);

    Uni<Product> update(Product product);

    Uni<Void> delete(Long id);
}
