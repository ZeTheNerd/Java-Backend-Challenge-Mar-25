package com.challenge.rest;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.challenge.rest.pojos.CalculatorTask;

@Configuration
@EnableKafka
public class KafkaConfig {
    
    @Value("${kafka.topic.calculator-request-topic}")
    private String requestTopic;
    @Value("${kafka.topic.calculator-reply-topic}")
    private String replyTopic;
    
    @Bean
    public ConcurrentMessageListenerContainer<String, BigDecimal> repliesContainer(
        ConcurrentKafkaListenerContainerFactory<String, BigDecimal> containerFactory) {
            return containerFactory.createContainer(replyTopic);
        }


    @Bean
    public ReplyingKafkaTemplate<String, CalculatorTask, BigDecimal> replyingKafkaTemplate(
        ProducerFactory<String, CalculatorTask> pf,
        ConcurrentMessageListenerContainer<String, BigDecimal> repliesContainer) {
            return new ReplyingKafkaTemplate<>(pf, repliesContainer);
        }

    @Bean
    public NewTopic taskRequests() {
        return TopicBuilder.name(requestTopic).build();
    }

    @Bean
    public NewTopic taskResults() {
        return TopicBuilder.name(replyTopic).build();
    }
    
}
