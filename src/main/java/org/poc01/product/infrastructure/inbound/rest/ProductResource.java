package org.poc01.product.infrastructure.inbound.rest;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import org.poc01.product.application.command.CreateProductCommand;
import org.poc01.product.application.command.handler.CreateProductHandler;
import org.poc01.product.application.port.in.ProductUseCase;
import org.poc01.product.application.service.ProductService;

import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class ProductResource {

    private final ProductUseCase productUseCase;
    private final CreateProductHandler createProductHandler;
    private final ProductService productService;

    @GET
    public Uni<List<ProductResponse>> findAll() {
        return productUseCase.findAll()
                .map(products ->
                        products.stream()
                                .map(ProductResponseMapper::toResponse)
                                .toList()
                );
    }

    @GET
    @Path("/{id}")
    public Uni<ProductResponse> findById(
            @PathParam("id") Long id) {

        return productUseCase.findById(id)
                .map(ProductResponseMapper::toResponse);
    }

    @POST
    public Uni<ProductResponse> create(ProductRequest request) {

        CreateProductCommand command = new CreateProductCommand(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getActive()
        );

        return createProductHandler.handle(command)
                .flatMap(product ->
                        productService.publishProductCreated(product)
                                .replaceWith(product)
                )
                .map(ProductResponseMapper::toResponse);
    }


    @PUT
    @Path("/{id}")
    public Uni<ProductResponse> update(
            @PathParam("id") Long id,
            ProductRequest request) {

        return productUseCase.update(
                        id,
                        ProductRequestMapper.toDomain(request)
                )
                .map(ProductResponseMapper::toResponse);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Void> delete(
            @PathParam("id") Long id) {

        return productUseCase.delete(id);
    }
}
