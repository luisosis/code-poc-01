package org.poc01.product.application.command.handler;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.poc01.product.application.command.CreateProductCommand;
import org.poc01.product.application.port.in.ProductUseCase;
import org.poc01.product.domain.model.Product;

@ApplicationScoped
public class CreateProductHandler {

    private final ProductUseCase productUseCase;

    public CreateProductHandler(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    public Uni<Product> handle(CreateProductCommand command) {

        Product product = new Product(
                null,
                command.name(),
                command.description(),
                command.price(),
                command.active()
        );

        return productUseCase.create(product);
    }
}
