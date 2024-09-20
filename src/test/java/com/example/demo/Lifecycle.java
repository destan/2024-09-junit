package com.example.demo;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class Lifecycle {

    @BeforeAll
    static void initializeExternalResources() {
        System.out.println("BeforeAll");
    }

    @BeforeEach
    void initializeMockObjects() {
        System.out.println("BeforeEach");
    }

    void fooTest() {
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXX");
        assertTrue(false);
    }

    @Test
    void someTest() {
        System.out.println("Thread someTest " + Thread.currentThread().getName() + "  " + this.hashCode());
        System.out.println("Running some test...");
        assertTrue(true);
    }

    @Test
    void otherTest() {

        //assertTrue(false);

        assumeTrue(false);

        System.out.println("Running another test...");
        assertNotEquals(1, 42, "Why would these be the same?");
    }

    @Test
    @Disabled
    void disabledTest() {
        assertTrue(true);
    }

    @AfterEach
    void tearDown() {
        System.out.println("AfterEach");
    }

    @AfterAll
    static void freeExternalResources() {
        System.out.println("AfterAll");
    }

}