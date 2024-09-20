package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@TestInstance(PER_CLASS)
class TcknBlacklistTest {

    private final TcknBlacklist tcknBlacklist = new TcknBlacklist();

    public TcknBlacklistTest() {
        System.out.println("TcknBlacklistTest constructor");
    }

    @ParameterizedTest
    @DisplayName("Return true if tckn is blacklisted")
    @ValueSource(strings = {"96158515640", "10897113684", "91390193410"})
    void blacklisted(String tckn) {
        assertTrue(tcknBlacklist.isBlacklisted(tckn));
    }

    @ParameterizedTest
    @DisplayName("Return false if tckn is not blacklisted")
    @ValueSource(strings = {"55103954316", "12384316806", "49508798842", "123456789012", "123", ""})
    void notBlacklisted(String tckn) {
        assertFalse(tcknBlacklist.isBlacklisted(tckn));
    }

}