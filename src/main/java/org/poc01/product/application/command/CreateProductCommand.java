package org.poc01.product.application.command;

import java.math.BigDecimal;

public record CreateProductCommand(
        String name,
        String description,
        BigDecimal price,
        Boolean active
) {
}
