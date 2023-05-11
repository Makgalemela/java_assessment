package com.matome.ledger.account.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RequestQueue {

    private final QueueConfig queueConfig;

    public RequestQueue(QueueConfig queueConfig) {
        this.queueConfig = queueConfig;
    }

    @Bean
    public MessageConverter jackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public DirectExchange serviceExchange() {
        return new DirectExchange(queueConfig.getExchange());
    }

    @Bean
    public AsyncRabbitTemplate RabbitTemplate(RabbitTemplate rabbitTemplate){
        return new AsyncRabbitTemplate(rabbitTemplate);
    }

    @Bean
    public Long serviceReplyTimeout() {
        return Long.valueOf(queueConfig.getServiceReplyTimeout());
    }


    @Bean
    public Queue serviceQueueInfo() {
        // Queue properties
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", Long.valueOf(queueConfig.getXMessageTtl()));
        args.put("x-max-length", Long.valueOf(queueConfig.getXMaxLength()));
        args.put("x-overflow", queueConfig.getXOverflow());

        // Dead letter messages
        args.put("x-dead-letter-exchange", String.format("dlx.%s", queueConfig.getExchange()));
        args.put("x-dead-letter-routing-key", DLRoutingKey());

        return new Queue(queueConfig.getQueueName(), false, false, false, args);
    }

    @Bean
    public Binding Binding(DirectExchange  serviceExchange, Queue serviceQueueInfo) {
        return BindingBuilder.bind(serviceQueueInfo)
                .to(serviceExchange())
                .with(serviceRoutingKey());
    }

    @Bean
    public int serviceQueueConsumers() {
        return Integer.valueOf(queueConfig.getNumberOfConsumers());
    }

    @Bean
    public String serviceRoutingKey() {
        return queueConfig.getRoutingKey();
    }

    /*
    *  Dead Letter Exchange and Queue Configurations for handling errors
     */

    @Bean
    public String DLRoutingKey() {
        return String.format("dl.%s", queueConfig.getRoutingKey());
    }

}
