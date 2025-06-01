package com.example.demo.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorServiceTest {

    private final CalculatorService calculatorService = new CalculatorService();

    @Test
    void testAddition() {
        assertEquals(5, calculatorService.add(2, 3), "2 + 3 sollte 5 ergeben");
        assertEquals(0, calculatorService.add(-2, 2), "-2 + 2 sollte 0 ergeben");
        assertEquals(-5, calculatorService.add(-2, -3), "-2 + -3 sollte -5 ergeben");
        assertEquals(-6, calculatorService.add(-2, -4), "-2 + -3 sollte -6 ergeben");
    }

}

