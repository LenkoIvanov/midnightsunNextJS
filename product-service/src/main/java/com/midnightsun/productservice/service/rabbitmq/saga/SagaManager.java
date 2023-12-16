package com.midnightsun.productservice.service.rabbitmq.saga;

import com.midnightsun.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class SagaManager {

    @Value("${rabbitmq.exchanges.saga-exchange}")
    private String sagaExchange;

    @Value("${rabbitmq.routings.os_saga_key}")
    private String secondSagaRoutingKey;

    private final RabbitTemplate rabbitTemplate;
    private final ProductService productService;

    public SagaManager(RabbitTemplate rabbitTemplate, ProductService productService) {
        this.rabbitTemplate = rabbitTemplate;
        this.productService = productService;
    }

    @RabbitListener(queues = {"${rabbitmq.queues.ps_saga_queue}"})
    private void productsValidation(SagaMessage sagaMessage) {
        log.debug("Received sagaMessage {} for saga from Order Service", sagaMessage);

        final var updatedMap = productService.checkProductsAvailability(sagaMessage.getProductIdQuantityMap());
        if (updatedMap != null) {
            sagaMessage.setProductIdQuantityMap(updatedMap);
        } else {
            sagaMessage.setIsTransactionSuccessful(Boolean.FALSE);
        }

        rabbitTemplate.convertAndSend(sagaExchange, secondSagaRoutingKey, sagaMessage);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class SagaMessage {
        private Boolean isTransactionSuccessful;
        private UUID orderId;
        private Map<Long, Long> productIdQuantityMap;
    }
}
