package com.matome.ledger.account.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Getter
@Configuration
public class QueueConfig {


    @Value("${rabbitmq.queue.configurations.exchange}")
    private String exchange;

    @Value("${rabbitmq.queue.configurations.x-message-ttl}")
    private String xMessageTtl;

    @Value("${rabbitmq.queue.configurations.x-max-length}")
    private String xMaxLength;

    @Value("${rabbitmq.queue.configurations.x-overflow}")
    private String xOverflow;

    @Value("${rabbitmq.queue.configurations.queue-name}")
    private String queueName;

    @Value("${rabbitmq.queue.configurations.number-of-consumers}")
    private String numberOfConsumers;

    @Value("${rabbitmq.queue.configurations.routing-key}")
    private String routingKey;

    @Value("${rabbitmq.queue.configurations.service-reply-timeout}")
    private String serviceReplyTimeout;
}


