package org.superbiz.moviefun.rabbit;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.superbiz.moviefun.albums.AlbumUpdateMessageConsumer;

/**
 * Created by 103209 on 14/09/17.
 */
@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.uri}") String rabbitMqUri;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setUri(rabbitMqUri);
        return factory;
    }

    @Value("${rabbitmq.queue}") String rabbitMqQueue;

    @Bean
    public IntegrationFlow amqpInbound(ConnectionFactory connectionFactory, AlbumUpdateMessageConsumer consumer) {
        return IntegrationFlows
                .from(Amqp.inboundAdapter(connectionFactory, rabbitMqQueue))
                .handle(consumer::consume)
                .get();
    }
}