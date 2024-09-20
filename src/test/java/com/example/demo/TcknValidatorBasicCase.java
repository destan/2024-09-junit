package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TcknValidatorBasicCase {

    TcknValidator tcknValidator = new TcknValidator(new TcknBlacklist());

    @Test
    @DisplayName("Validator returns true for valid tckn")
    void valid() {
        assertTrue(tcknValidator.validate("32286582130"));
    }

    @Test
    @DisplayName("Validator returns false for invalid tckn")
    void invalid() {
        assertFalse(tcknValidator.validate("12345678901"));
    }

    @Test
    @DisplayName("Validator returns false for less than 11 chars")
    void lessThan11Chars() {
        //boolean isThrown = false;
        //try {
        //    tcknValidator.validate("3228658213");
        //} catch (IllegalArgumentException e) {
        //    isThrown = true;
        //    assertEquals("Tckn should be exactly 11 digits", e.getMessage());
        //}
        //assertTrue(isThrown);

        //=============================================================

        var e = assertThrows(IllegalArgumentException.class, () -> tcknValidator.validate("3228658213"));
        assertEquals("Tckn should be exactly 11 digits", e.getMessage());
    }

    @Test
    @DisplayName("Validator returns false for more than 11 chars")
    void moreThan11Chars() {
        var e = assertThrows(IllegalArgumentException.class, () -> tcknValidator.validate("322865821300"));
        assertEquals("Tckn should be exactly 11 digits", e.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Should be true for valid tckn list")
    @ValueSource(strings = {"78229126412", "59369401232", "63931593492"})
    void validNos(String tckn) {
        assertTrue(tcknValidator.validate(tckn));
    }

    @ParameterizedTest
    @DisplayName("Should be false for invalid tckn list")
    @ValueSource(strings = {"12345678901", "10000000000", "22222222222"})
    void invalidNos(String tckn) {
        assertFalse(tcknValidator.validate(tckn));
    }

    @ParameterizedTest
    @DisplayName("Should be the same from the list")
    @MethodSource("tcknMap")
    void fromList(TcknSource tcknSource) {

       if (tcknSource.valid()) {
           assertTrue(tcknValidator.validate(tcknSource.tckn));
       }
       else {
           assertFalse(tcknValidator.validate(tcknSource.tckn));
       }
    }

    static Stream<TcknSource> tcknMap() {
        return Stream.of(
                new TcknSource("78229126412", true),
                new TcknSource("59369401232", true),
                new TcknSource("63931593492", true),
                new TcknSource("12345678901", false),
                new TcknSource("10000000000", false),
                new TcknSource("22222222222", false)
        );
    }

    record TcknSource(String tckn, boolean valid) {}

    //static class TcknSource {
    //    private final String tckn;
    //    private final boolean valid;
    //
    //    public TcknSource(String tckn, boolean valid) {
    //        this.tckn = tckn;
    //        this.valid = valid;
    //    }
    //
    //    public String getTckn() {
    //        return tckn;
    //    }
    //
    //    public boolean isValid() {
    //        return valid;
    //    }
    //}

    @ParameterizedTest
    @DisplayName("Should be the same from the list")
    @CsvSource({
            "78229126412, true",
            "59369401232, true",
            "63931593492, true",
            "12345678901, false",
            "10000000000, false",
            "22222222222, false"})
    void fromCsv(String tckn, boolean valid) {

        if (valid) {
            assertTrue(tcknValidator.validate(tckn));
        }
        else {
            assertFalse(tcknValidator.validate(tckn));
        }
    }

    @ParameterizedTest
    @DisplayName("Should be the same from the list")
    //@CsvFileSource(files = "src/test/resources/tcknList.csv")
    @CsvFileSource(resources = "/tcknList.csv")
    void fromFile(String tckn, boolean valid) {

        if (valid) {
            assertTrue(tcknValidator.validate(tckn));
        }
        else {
            assertFalse(tcknValidator.validate(tckn));
        }
    }
}
