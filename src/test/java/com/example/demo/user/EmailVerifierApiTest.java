package com.example.demo.user;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class EmailVerifierApiTest {

    @Test
    void verifyEmail_anyInput_executesInExpectedDuration() {
        EmailVerifierApi emailVerifierApi = new EmailVerifierApi();

        assertTimeout(Duration.of(3050, ChronoUnit.MILLIS), () -> emailVerifierApi.checkEmail("any@email.com"));
    }

}