package com.midnightsun.productservice.service.rabbitmq.rpc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.midnightsun.productservice.service.ProductService;
import com.midnightsun.productservice.service.dto.external.OrderItemExtendedInfoDTO;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class ExternalProductService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final ProductService productService;

    public ExternalProductService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, ProductService productService) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.productService = productService;
    }

    @RabbitListener(queues = "${rabbitmq.queues.ps_queue}")
    public void process(Message message) throws IOException {
        //TODO: add error handling
        byte[] body = message.getBody();
        List<Long> productIds = objectMapper.readValue(body, new TypeReference<>() {});

        Map<Long, OrderItemExtendedInfoDTO> productIdInfoMap = new HashMap<>();
        for (Long id : productIds) {
            //TODO: Make batch operation
            final var product = productService.getOne(id);
            final var orderItemExtended = new OrderItemExtendedInfoDTO();

            //TODO: Add base64Image
            orderItemExtended.setName(product.getName());
            orderItemExtended.setPrice(product.getPrice());
            orderItemExtended.setDescription(product.getDescription());

            productIdInfoMap.put(id, orderItemExtended);
        }

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);

        Message build = MessageBuilder.withBody(objectMapper.writeValueAsBytes(productIdInfoMap))
                .andProperties(messageProperties).build();

        String replyTo = message.getMessageProperties().getReplyTo();

        rabbitTemplate.sendAndReceive("", replyTo, build);
    }
}
