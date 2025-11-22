package br.com.safework.messaging;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE = "safework.alerts.q";
    public static final String EXCHANGE = "safework.alerts.x";
    public static final String ROUTING_KEY = "alerts.created";

    @Bean
    Queue queue() { return new Queue(QUEUE, true); }

    @Bean
    DirectExchange exchange() { return new DirectExchange(EXCHANGE); }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}
