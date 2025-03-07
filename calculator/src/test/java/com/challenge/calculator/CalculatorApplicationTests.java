package com.challenge.calculator;

import java.math.BigDecimal;
import java.util.UUID;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import com.challenge.calculator.pojos.CalculatorOperation;
import com.challenge.calculator.pojos.CalculatorTask;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(topics={"${kafka.topic.calculator-request-topic}","${kafka.topic.calculator-reply-topic}"})
class CalculatorApplicationTests {

    @Value("${kafka.topic.calculator-request-topic}")
    private String requestTopic;
    @Value("${kafka.topic.calculator-reply-topic}")
    private String replyTopic;

    @Autowired
    private KafkaTemplate<String, CalculatorTask> template;

    @Test
    public void whenSumRequestReceived_thenCorrectResultSent() {
        
        String id = UUID.randomUUID().toString();
        
        // create sum operation
        BigDecimal first = new BigDecimal(0.005);
        BigDecimal second = new BigDecimal(2);
        CalculatorTask task = new CalculatorTask(CalculatorOperation.SUM, first, second);

        // send operation to request topic
        ProducerRecord<String, CalculatorTask> sendRecord = new ProducerRecord<String,CalculatorTask>(requestTopic, id, task);
        template.send(sendRecord);

        // check result published in reply topic WIP
    }
}
