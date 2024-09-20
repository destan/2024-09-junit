package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(OrderAnnotation.class)
class Demo {

    @Test
    @Order(1)
    @DisplayName("Some method should be true")
    void someMethod() {
        assertTrue(true);
        assertFalse(false);


    }

    @Test
    @Order(2)
    @DisplayName("Another method should be something")
    void someMethod2() {
        assertTrue(true);
        assertFalse(false);
    }

    @Test
    @Order(3)
    @DisplayName("Be it may")
    void someMethod3() {
        assertTrue(true);
        assertFalse(false);
    }

    @Test
    @DisplayName("Free test method")
    void someMethod4() {
        assertTrue(true);
        assertFalse(false);
    }

}
