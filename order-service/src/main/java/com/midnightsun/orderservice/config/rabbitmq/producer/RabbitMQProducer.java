package com.midnightsun.orderservice.config.rabbitmq.producer;

import com.midnightsun.orderservice.model.Order;
import com.midnightsun.orderservice.service.dto.OrderDTO;
import com.rabbitmq.client.AMQP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class RabbitMQProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEmailForCreatedOrder(OrderDTO order) {
        log.debug("Sending ORDER {} to Notification Service", order.getId());
        rabbitTemplate.convertAndSend(exchange, routingKey, order);
    }

    public Boolean areProductsAvailable() {
//        // Create a unique correlation ID for the request
//        String correlationId = UUID.randomUUID().toString();
//
//        // Create a callback queue for receiving the response
//        String callbackQueueName = channel.queueDeclare().getQueue();
//
//        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
//                .correlationId(correlationId)
//                .replyTo(callbackQueueName)
//                .build();
//
//        // Send the request message to the product service
//        channel.basicPublish(
//                "direct-exchange",
//                "product-service-queue",
//                properties,
//                message.getBytes()
//        );
//
//        // Wait for the response on the callback queue
//        QueueingConsumer consumer = new QueueingConsumer(channel);
//        channel.basicConsume(callbackQueueName, true, consumer);
//
//        // Process the response when it arrives
//        while (true) {
//            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
//            if (delivery.getProperties().getCorrelationId().equals(correlationId)) {
//                String response = new String(delivery.getBody());
//                // Process the response from the product service
//                break;
//            }
//        }
        return true;
    }
}
