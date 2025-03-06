package com.challenge.rest.pojos;

import java.math.BigDecimal;

public class CalculatorTask {
    private CalculatorOperation operation;
    private BigDecimal first;
    private BigDecimal second;
    
    public CalculatorTask(CalculatorOperation op, BigDecimal first, BigDecimal second) {
        this.operation = op;
        this.first = first;
        this.second = second;
    }

    public CalculatorOperation getOperation() {return operation;}
    public void setOperation(CalculatorOperation op) {operation = op;}

    public BigDecimal getFirst() {return first;}
    public void setFirst(BigDecimal first) {this.first = first;}

    public BigDecimal getSecond() {return second;}
    public void setSecond(BigDecimal second) {this.second = second;}

}