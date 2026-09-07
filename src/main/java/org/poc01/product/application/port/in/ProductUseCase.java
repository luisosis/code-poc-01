package org.poc01.product.application.port.in;

import io.smallrye.mutiny.Uni;
import org.poc01.product.domain.model.Product;

import java.util.List;

public interface ProductUseCase {


    Uni<List<Product>> findAll();

    Uni<Product> findById(Long id);

    Uni<Product> create(Product product);

    Uni<Product> update(Long id, Product product);

    Uni<Void> delete(Long id);

}
