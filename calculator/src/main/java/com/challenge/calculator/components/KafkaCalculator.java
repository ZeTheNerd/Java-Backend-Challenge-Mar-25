package com.challenge.calculator.components;

import java.math.BigDecimal;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.challenge.calculator.pojos.CalculatorTask;

@Component
public class KafkaCalculator {
    
    // method responsible for consuming the request topic,
    // computing the operation and sending the result to the result topic
    @KafkaListener(topics="${kafka.topic.calculator-request-topic}")
    @SendTo("${kafka.topic.calculator-reply-topic}")
    public BigDecimal processRequest(ConsumerRecord<String, CalculatorTask> record) {
        // get request ID from the record's key
        String requestID = record.key();
        // get requested operation from the record's value
        CalculatorTask task = record.value();

        // execute the corresponding operation
        BigDecimal first = task.getFirst();
        BigDecimal second = task.getSecond();
        BigDecimal result = null;
        switch (task.getOperation()) {
            case SUM:
                result = first.add(second);
                break;
            case SUBTRACTION:
                result = first.subtract(second);
                break;
            case MULTIPLICATION:
                result = first.multiply(second);
                break;
            case DIVISION:
                result = first.divide(second);
                break;
        }

        // send result to result topic
        return result;
    }
}
