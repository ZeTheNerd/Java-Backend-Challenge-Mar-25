package com.challenge.rest.pojos;

import java.math.BigDecimal;

enum Operation {
    SUM,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION
}

public class CalculatorTask {
    private Operation operation;
    private BigDecimal first;
    private BigDecimal second;
    
    public CalculatorTask(Operation op, BigDecimal a, BigDecimal b) {
        operation = op;
        first = a;
        second = b;
    }

    public Operation getOperation() {return operation;}
    public void setOperation(Operation op) {operation = op;}

    public BigDecimal getFirst() {return first;}
    public void setFirst(BigDecimal first) {this.first = first;}

    public BigDecimal getSecond() {return second;}
    public void setSecond(BigDecimal second) {this.second = second;}

}