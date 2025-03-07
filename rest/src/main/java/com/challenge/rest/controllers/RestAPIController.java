package com.challenge.rest.controllers;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
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

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class RestAPIController {
    Logger logger = LoggerFactory.getLogger(RestAPIController.class);
    private String mdcKey = "requestID";

    @Value("${kafka.topic.calculator-request-topic}")
    private String requestTopic;

    @Autowired
    private ReplyingKafkaTemplate<String, CalculatorTask, BigDecimal> replyingTemplate;

    // GET /sum?a=<BigDecimal>&b=<BigDecimal>
    // Endpoint for sum operation
    @GetMapping(value="sum", produces={MediaType.APPLICATION_JSON_VALUE})
    public String getSum(@RequestParam BigDecimal a, @RequestParam BigDecimal b, HttpServletResponse response)
    throws InterruptedException, ExecutionException, TimeoutException {
        // create unique ID for this request
        String id = UUID.randomUUID().toString();
        MDC.put(mdcKey, id);

        // create message to be sent to calculator via kafka
        logger.info("Received request for SUM a=" + a + " b=" + b);
        CalculatorTask sum = new CalculatorTask(CalculatorOperation.SUM, a, b);
        ProducerRecord<String, CalculatorTask> record = new ProducerRecord<String,CalculatorTask>(requestTopic, id, sum);

        // send mesage to request topic
        logger.info("Sending request for SUM a=" + a + " b=" + b + " to kafka");
        RequestReplyFuture<String, CalculatorTask, BigDecimal> future = replyingTemplate.sendAndReceive(record);
        // await for reply in results topic
        ConsumerRecord<String, BigDecimal> result = future.get(5, TimeUnit.SECONDS);
        logger.info("Received result for SUM a=" + a + " b=" + b + " from kafka: " + result.value());

        // add request ID to response headers
        response.addHeader("X-Request-Id", id);

        MDC.remove(mdcKey);
        return "{\"result\": " + result.value() + "}";
    }

    // GET /subtraction?a=<BigDecimal>&b=<BigDecimal>
    // Endpoint for subtraction operation
    @GetMapping(value="subtraction", produces=MediaType.APPLICATION_JSON_VALUE)
    public String getSubtraction(@RequestParam BigDecimal a, @RequestParam BigDecimal b, HttpServletResponse response)
    throws InterruptedException, ExecutionException, TimeoutException {
        // create unique ID for this request
        String id = UUID.randomUUID().toString();
        MDC.put(mdcKey, id);

        // create message to be sent to calculator via kafka
        logger.info("Received request for SUBTRACTION a=" + a + " b=" + b);
        CalculatorTask subtraction = new CalculatorTask(CalculatorOperation.SUBTRACTION, a, b);
        ProducerRecord<String, CalculatorTask> record = new ProducerRecord<String,CalculatorTask>(requestTopic, id, subtraction);

        // send mesage to request topic
        logger.info("Sending request for SUBTRACTION a=" + a + " b=" + b + " to kafka");
        RequestReplyFuture<String, CalculatorTask, BigDecimal> future = replyingTemplate.sendAndReceive(record);
        // await for reply in results topic
        ConsumerRecord<String, BigDecimal> result = future.get(5, TimeUnit.SECONDS);
        logger.info("Received result for SUBTRACTION a=" + a + " b=" + b + " from kafka: " + result.value());

        // add request ID to response headers
        response.addHeader("X-Request-Id", id);

        MDC.remove(mdcKey);
        return "{\"result\": " + result.value() + "}";
    }

    // GET /multiplication?a=<BigDecimal>&b=<BigDecimal>
    // Endpoint for multiplication operation
    @GetMapping(value="multiplication", produces=MediaType.APPLICATION_JSON_VALUE)
    public String getMultiplication(@RequestParam BigDecimal a, @RequestParam BigDecimal b, HttpServletResponse response)
    throws InterruptedException, ExecutionException, TimeoutException {
        // create unique ID for this request
        String id = UUID.randomUUID().toString();
        MDC.put(mdcKey, id);

        // create message to be sent to calculator via kafka
        logger.info("Received request for MULTIPLICATION a=" + a + " b=" + b);
        CalculatorTask multiplication = new CalculatorTask(CalculatorOperation.MULTIPLICATION, a, b);
        ProducerRecord<String, CalculatorTask> record = new ProducerRecord<String,CalculatorTask>(requestTopic, id, multiplication);

        // send mesage to request topic
        logger.info("Sending request for MULTIPLICATION a=" + a + " b=" + b + " to kafka");
        RequestReplyFuture<String, CalculatorTask, BigDecimal> future = replyingTemplate.sendAndReceive(record);
        // await for reply in results topic
        ConsumerRecord<String, BigDecimal> result = future.get(5, TimeUnit.SECONDS);
        logger.info("Received result for MULTIPLICATION a=" + a + " b=" + b + " from kafka: " + result.value());

        // add request ID to response headers
        response.addHeader("X-Request-Id", id);

        MDC.remove(mdcKey);
        return "{\"result\": " + result.value() + "}";
    }

    // GET /division?a=<BigDecimal>&b=<BigDecimal>
    // Endpoint for multiplication operation
    @GetMapping(value="division", produces=MediaType.APPLICATION_JSON_VALUE)
    public String getDivision(@RequestParam BigDecimal a, @RequestParam BigDecimal b, HttpServletResponse response)
    throws InterruptedException, ExecutionException, TimeoutException {
        // create unique ID for this request
        String id = UUID.randomUUID().toString();
        MDC.put(mdcKey, id);

        // create message to be sent to calculator via kafka
        logger.info("Received request for DIVISION a=" + a + " b=" + b);
        CalculatorTask division = new CalculatorTask(CalculatorOperation.DIVISION, a, b);
        ProducerRecord<String, CalculatorTask> record = new ProducerRecord<String,CalculatorTask>(requestTopic, id, division);

        // send mesage to request topic
        logger.info("Sending request for DIVISION a=" + a + " b=" + b + " to kafka");
        RequestReplyFuture<String, CalculatorTask, BigDecimal> future = replyingTemplate.sendAndReceive(record);
        // await for reply in results topic
        ConsumerRecord<String, BigDecimal> result = future.get(5, TimeUnit.SECONDS);
        logger.info("Received result for DIVISION a=" + a + " b=" + b + " from kafka: " + result.value());

        // add request ID to response headers
        response.addHeader("X-Request-Id", id);

        MDC.remove(mdcKey);
        return "{\"result\": " + result.value() + "}";
    }
}
