package org.poc01.product.infrastructure.outbound.persistence;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.poc01.product.application.port.out.ProductRepository;
import org.poc01.product.domain.model.Product;

import java.util.List;

@ApplicationScoped
public class ProductRepositoryAdapter
        implements ProductRepository {

    @Inject
    ProductPanacheRepository repository;

    @Override
    public Uni<List<Product>> findAll() {

        return repository.find("order by id")
                .list()
                .map(entities ->
                        entities.stream()
                                .map(ProductEntityMapper::toDomain)
                                .toList()
                );
    }

    @Override
    public Uni<Product> findById(Long id) {

        return repository.findById(id)
                .map(ProductEntityMapper::toDomain);
    }

    @Override
    public Uni<Product> save(Product product) {

        ProductEntity entity =
                ProductEntityMapper.toEntity(product);

        return repository.persist(entity)
                .replaceWith(() ->
                        ProductEntityMapper.toDomain(entity)
                );
    }

    @Override
    public Uni<Product> update(Product product) {

        ProductEntity entity =
                ProductEntityMapper.toEntity(product);

        return repository.persist(entity)
                .replaceWith(() ->
                        ProductEntityMapper.toDomain(entity)
                );
    }

    @Override
    public Uni<Void> delete(Long id) {

        return repository.deleteById(id)
                .replaceWithVoid();
    }
}
