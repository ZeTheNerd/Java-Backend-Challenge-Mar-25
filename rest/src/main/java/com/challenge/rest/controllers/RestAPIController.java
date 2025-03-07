package com.challenge.rest.controllers;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
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
    public String getSum(@RequestParam BigDecimal a, @RequestParam BigDecimal b) throws InterruptedException, ExecutionException, TimeoutException {
        // create unique ID for this request
        String id = UUID.randomUUID().toString();

        // create message to be sent to calculator via kafka
        CalculatorTask sum = new CalculatorTask(CalculatorOperation.SUM, a, b);
        ProducerRecord<String, CalculatorTask> record = new ProducerRecord<String,CalculatorTask>(requestTopic, id, sum);

        // send mesage to request topic
        RequestReplyFuture<String, CalculatorTask, BigDecimal> future = replyingTemplate.sendAndReceive(record);
        // await for reply in results topic
        ConsumerRecord<String, BigDecimal> result = future.get(5, TimeUnit.SECONDS);

        return "{\"result\": " + result.value() + "}";
    }

    // GET /subtraction?a=<BigDecimal>&b=<BigDecimal>
    // Endpoint for subtraction operation
    @GetMapping(value="subtraction", produces=MediaType.APPLICATION_JSON_VALUE)
    public String getSubtraction(@RequestParam BigDecimal a, @RequestParam BigDecimal b) throws InterruptedException, ExecutionException, TimeoutException {
        // create unique ID for this request
        String id = UUID.randomUUID().toString();

        // create message to be sent to calculator via kafka
        CalculatorTask subtraction = new CalculatorTask(CalculatorOperation.SUBTRACTION, a, b);
        ProducerRecord<String, CalculatorTask> record = new ProducerRecord<String,CalculatorTask>(requestTopic, id, subtraction);

        // send mesage to request topic
        RequestReplyFuture<String, CalculatorTask, BigDecimal> future = replyingTemplate.sendAndReceive(record);
        // await for reply in results topic
        ConsumerRecord<String, BigDecimal> result = future.get(5, TimeUnit.SECONDS);

        return "{\"result\": " + result.value() + "}";
    }

    // GET /multiplication?a=<BigDecimal>&b=<BigDecimal>
    // Endpoint for multiplication operation
    @GetMapping(value="multiplication", produces=MediaType.APPLICATION_JSON_VALUE)
    public String getMultiplication(@RequestParam BigDecimal a, @RequestParam BigDecimal b) throws InterruptedException, ExecutionException, TimeoutException {
        // create unique ID for this request
        String id = UUID.randomUUID().toString();

        // create message to be sent to calculator via kafka
        CalculatorTask multiplication = new CalculatorTask(CalculatorOperation.MULTIPLICATION, a, b);
        ProducerRecord<String, CalculatorTask> record = new ProducerRecord<String,CalculatorTask>(requestTopic, id, multiplication);

        // send mesage to request topic
        RequestReplyFuture<String, CalculatorTask, BigDecimal> future = replyingTemplate.sendAndReceive(record);
        // await for reply in results topic
        ConsumerRecord<String, BigDecimal> result = future.get(5, TimeUnit.SECONDS);

        return "{\"result\": " + result.value() + "}";
    }

    // GET /division?a=<BigDecimal>&b=<BigDecimal>
    // Endpoint for multiplication operation
    @GetMapping(value="division", produces=MediaType.APPLICATION_JSON_VALUE)
    public String getDivision(@RequestParam BigDecimal a, @RequestParam BigDecimal b) throws InterruptedException, ExecutionException, TimeoutException {
        // create unique ID for this request
        String id = UUID.randomUUID().toString();

        // create message to be sent to calculator via kafka
        CalculatorTask division = new CalculatorTask(CalculatorOperation.DIVISION, a, b);
        ProducerRecord<String, CalculatorTask> record = new ProducerRecord<String,CalculatorTask>(requestTopic, id, division);

        // send mesage to request topic
        RequestReplyFuture<String, CalculatorTask, BigDecimal> future = replyingTemplate.sendAndReceive(record);
        // await for reply in results topic
        ConsumerRecord<String, BigDecimal> result = future.get(5, TimeUnit.SECONDS);

        return "{\"result\": " + result.value() + "}";
    }
}
