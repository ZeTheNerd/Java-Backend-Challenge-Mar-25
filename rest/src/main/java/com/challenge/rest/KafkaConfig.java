package com.challenge.rest;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
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
    
    // @Bean
    // public ProducerFactory<String, CalculatorTask> producerFactory() {
    //     return new DefaultKafkaProducerFactory<>(
    //         Map.of(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "broker:9096",
    //                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
    //                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class)
    //     );
    // }

    // @Bean
    // public ConsumerFactory<String, BigDecimal> consumerFactory() {
    //     return new DefaultKafkaConsumerFactory<>(
    //         Map.of(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "broker:9096",
    //                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
    //                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
    //                ConsumerConfig.GROUP_ID_CONFIG, "rest-consumer-group")
    //     );
    // }

    // @Bean
    // public ConcurrentKafkaListenerContainerFactory<String, BigDecimal> containerFactory() {
    //     ConcurrentKafkaListenerContainerFactory<String, BigDecimal> factory = new ConcurrentKafkaListenerContainerFactory<>();
    //     factory.setConsumerFactory(consumerFactory());
    //     return factory;
    // }
    
    @Bean
    public ConcurrentMessageListenerContainer<String, BigDecimal> repliesContainer(
        ConcurrentKafkaListenerContainerFactory<String, BigDecimal> containerFactory) {
            return containerFactory.createContainer("calculator-result");
        }


    @Bean
    public ReplyingKafkaTemplate<String, CalculatorTask, BigDecimal> replyingKafkaTemplate(
        ProducerFactory<String, CalculatorTask> pf,
        ConcurrentMessageListenerContainer<String, BigDecimal> repliesContainer) {
            return new ReplyingKafkaTemplate<>(pf, repliesContainer);
        }

    @Bean
    public NewTopic taskRequests() {
        return TopicBuilder.name("calculator-request").build();
    }

    @Bean
    public NewTopic taskResults() {
        return TopicBuilder.name("calculator-result").build();
    }
    
}
