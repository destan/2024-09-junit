package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TcknValidatorBlacklist {

    private static final String BLACKLISTED_TCKN = "10897113684";
    private static final String NOT_BLACKLISTED_TCKN = "57397140758";

    public TcknValidatorBlacklist() {
        System.out.println("TcknValidatorBlacklist constructor");
    }

    @Mock
    TcknBlacklist blacklist;

    private TcknValidator tcknValidator;

    @BeforeEach
    void setUp() {
        tcknValidator = new TcknValidator(blacklist);
        //when(blacklist.isBlacklisted(BLACKLISTED_TCKN)).thenReturn(true);
        //when(blacklist.isBlacklisted(NOT_BLACKLISTED_TCKN)).thenReturn(false);
    }

    @Test
    @DisplayName("Blacklisted tckn throws exception")
    void blacklistedTckn() {
        System.out.println("Thread blacklistedTckn " + Thread.currentThread().getName() + "  " + this.hashCode());
        when(blacklist.isBlacklisted(BLACKLISTED_TCKN)).thenReturn(true);
        var e = assertThrows(IllegalArgumentException.class, () -> tcknValidator.validate(BLACKLISTED_TCKN));
        assertEquals("Tckn " + BLACKLISTED_TCKN + " is blacklisted", e.getMessage());
    }

    @Test
    @DisplayName("Not blacklisted tckn validates")
    void notBlacklistedTckn() {
        when(blacklist.isBlacklisted(NOT_BLACKLISTED_TCKN)).thenReturn(false);
        assertTrue(tcknValidator.validate(NOT_BLACKLISTED_TCKN));
    }
}
