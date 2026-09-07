package org.poc01.product.application.port.out;

import io.smallrye.mutiny.Uni;
import org.poc01.product.domain.model.Product;

public interface ProductEventPublisher {

    Uni<Void> publishProductCreated(Product product);
}
