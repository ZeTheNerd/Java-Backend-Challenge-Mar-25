package com.challenge.calculator.components;

import java.math.BigDecimal;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.challenge.calculator.pojos.CalculatorTask;

@Component
public class KafkaCalculator {
    Logger logger = LoggerFactory.getLogger(KafkaCalculator.class);
    private String mdcKey = "requestID";
    
    // method responsible for consuming the request topic,
    // computing the operation and sending the result to the result topic
    @KafkaListener(topics="${kafka.topic.calculator-request-topic}")
    @SendTo("${kafka.topic.calculator-reply-topic}")
    public BigDecimal processRequest(ConsumerRecord<String, CalculatorTask> record) {
        // get request ID from the record's key
        String requestID = record.key();
        MDC.put(mdcKey, requestID);

        // get requested operation from the record's value
        logger.info("Received new calculator task for processing");
        CalculatorTask task = record.value();

        // execute the corresponding operation
        BigDecimal first = task.getFirst();
        BigDecimal second = task.getSecond();
        BigDecimal result = null;
        switch (task.getOperation()) {
            case SUM:
            result = first.add(second);
            logger.info("Executed SUM a=" + first + " b=" + second + " result=" +result);
                break;
            case SUBTRACTION:
                result = first.subtract(second);
                logger.info("Executed SUBTRACTION a=" + first + " b=" + second + " result=" +result);
                break;
            case MULTIPLICATION:
                result = first.multiply(second);
                logger.info("Executed MULTIPLICATION a=" + first + " b=" + second + " result=" +result);
                break;
            case DIVISION:
                result = first.divide(second);
                logger.info("Executed DIVISION a=" + first + " b=" + second + " result=" +result);
                break;
        }

        logger.info("Publishing result in Kafka");
        MDC.remove(mdcKey);
        // send result to result topic
        return result;
    }
}
