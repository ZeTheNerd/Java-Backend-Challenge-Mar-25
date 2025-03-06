package com.challenge.calculator.components;

import java.math.BigDecimal;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.challenge.calculator.pojos.CalculatorTask;

@Component
public class KafkaCalculator {
    
    //@KafkaListener(topics="${kafka.topic.calculator-request-topic}")
    @KafkaListener(topics="calculator-request")
    @SendTo("calculator-result")
    public BigDecimal processRequest(CalculatorTask task) {
        BigDecimal result = task.getFirst().add(task.getSecond());
        return result;
    }
}
