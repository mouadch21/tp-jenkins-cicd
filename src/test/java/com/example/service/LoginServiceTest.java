package com.example.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {

    private final LoginService loginService = new LoginService();

    @Test
    void shouldAcceptValidCredentials() {
        assertTrue(loginService.authenticate("admin", "smartcampus123"));
    }

    @Test
    void shouldRejectInvalidPassword() {
        assertFalse(loginService.authenticate("admin", "wrong"));
    }

    @Test
    void shouldRejectUnknownUsername() {
        assertFalse(loginService.authenticate("unknown", "smartcampus123"));
    }
}
