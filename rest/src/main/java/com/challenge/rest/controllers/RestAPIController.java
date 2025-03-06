package com.challenge.rest.controllers;

import java.math.BigDecimal;
import java.util.UUID;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.rest.pojos.CalculatorOperation;
import com.challenge.rest.pojos.CalculatorTask;

@RestController
public class RestAPIController {

    @Value("${kafka.topic.calculator-request-topic}")
    private String requestTopic;

    @Autowired
    private ReplyingKafkaTemplate<String, CalculatorTask, BigDecimal> replyingTemplate;

    // GET /sum?a=<BigDecimal>&b=<BigDecimal>
    // Endpoint for sum operation
    @GetMapping(value="sum", produces={MediaType.APPLICATION_JSON_VALUE})
    public BigDecimal getSum(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        // create unique ID for this request
        String id = UUID.randomUUID().toString();

        // create message to be sent to calculator via kafka
        CalculatorTask sum = new CalculatorTask(CalculatorOperation.SUM, a, b);
        ProducerRecord<String, CalculatorTask> record = new ProducerRecord<String,CalculatorTask>(requestTopic, id, sum);

        // send mesage to broker
        replyingTemplate.sendAndReceive(record);


        return a.add(b);
    }

    // GET /subtraction?a=<BigDecimal>&b=<BigDecimal>
    // Endpoint for subtraction operation
    @GetMapping(value="subtraction", produces=MediaType.APPLICATION_JSON_VALUE)
    public BigDecimal getSubtraction(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return a.subtract(b);
    }

    // GET /multiplication?a=<BigDecimal>&b=<BigDecimal>
    // Endpoint for multiplication operation
    @GetMapping(value="multiplication", produces=MediaType.APPLICATION_JSON_VALUE)
    public BigDecimal getMultiplication(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return a.multiply(b);
    }

    // GET /division?a=<BigDecimal>&b=<BigDecimal>
    // Endpoint for multiplication operation
    @GetMapping(value="division", produces=MediaType.APPLICATION_JSON_VALUE)
    public BigDecimal getDivision(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return a.divide(b);
    }
}
