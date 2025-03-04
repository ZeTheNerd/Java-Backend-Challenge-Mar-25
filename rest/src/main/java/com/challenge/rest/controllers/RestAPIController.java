package com.challenge.rest.controllers;

import java.math.BigDecimal;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAPIController {

    // GET /sum?a=<BigDecimal>&b=<BigDecimal>
    // Endpoint for sum operation
    @GetMapping(value="sum", produces={MediaType.APPLICATION_JSON_VALUE})
    public BigDecimal getSum(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
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
}
