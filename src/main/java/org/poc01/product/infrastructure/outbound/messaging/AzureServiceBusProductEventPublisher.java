package org.poc01.product.infrastructure.outbound.messaging;


import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderAsyncClient;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.poc01.product.application.port.out.ProductEventPublisher;
import org.poc01.product.domain.model.Product;

@ApplicationScoped
public class AzureServiceBusProductEventPublisher implements ProductEventPublisher {

    @ConfigProperty(name = "azure.servicebus.connection-string")
    String connectionString;

    @ConfigProperty(name = "azure.servicebus.topic")
    String topic;

    private ServiceBusSenderAsyncClient sender;

    @PostConstruct
    void init() {

        sender = new ServiceBusClientBuilder()
                .connectionString(connectionString)
                .sender()
                .topicName(topic)
                .buildAsyncClient();
    }

    @Override
    public Uni<Void> publishProductCreated(Product product) {

        String message = product.getId().toString();

        ServiceBusMessage serviceBusMessage =
                new ServiceBusMessage(message);

        return Uni.createFrom()
                .completionStage(
                        sender.sendMessage(serviceBusMessage).toFuture()
                )
                .replaceWithVoid();
    }


    @PreDestroy
    void destroy() {
        if (sender != null) {
            sender.close();
        }
    }
}
