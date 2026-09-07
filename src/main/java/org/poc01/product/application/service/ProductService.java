package org.poc01.product.application.service;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.poc01.product.application.port.in.ProductUseCase;
import org.poc01.product.application.port.out.ProductEventPublisher;
import org.poc01.product.application.port.out.ProductRepository;
import org.poc01.product.domain.model.Product;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ProductService implements ProductUseCase {

    private final ProductRepository productRepository;
    private final ProductEventPublisher eventPublisher;

    @WithSession
    @Override
    public Uni<List<Product>> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Uni<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @WithTransaction
    @Override
    public Uni<Product> create(Product product) {

        log.info("1. Entrando a ProductService.create()");

        return productRepository.save(product)
                .invoke(savedProduct ->
                        log.info(
                                "2. Producto guardado. ID: {}",
                                savedProduct.getId()
                        )
                );
    }

    public Uni<Void> publishProductCreated(Product product) {

        log.info(
                "3. Publicando evento ProductCreated. ID: {}",
                product.getId()
        );

        return eventPublisher
                .publishProductCreated(product)
                .invoke(() ->
                        log.info(
                                "4. Evento ProductCreated enviado correctamente. ID: {}",
                                product.getId()
                        )
                )
                .onFailure()
                .invoke(error ->
                        log.error(
                                "ERROR enviando evento ProductCreated. ID: {}",
                                product.getId(),
                                error
                        )
                );
    }

    @Override
    public Uni<Product> update(Long id, Product product) {
        product.setId(id);
        return productRepository.update(product);
    }

    @Override
    public Uni<Void> delete(Long id) {
        return productRepository.delete(id);
    }
}
