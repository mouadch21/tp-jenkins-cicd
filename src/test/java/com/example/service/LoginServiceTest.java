
package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.model.User;

class LoginServiceTest {

    private LoginService loginService;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        loginService = new LoginService();
        loginService.register(
            new User(1L, "mouad@example.com", "Password123!")
        );
    }

    @Test
    void shouldLoginWithCorrectCredentials() {
        assertTrue(
            loginService.login("mouad@example.com", "Password123!")
        );
    }

    @Test
    void shouldRejectIncorrectPassword() {
        assertFalse(
            loginService.login("mouad@example.com", "WrongPassword")
        );
    }

    @Test
    void shouldRejectUnknownEmail() {
        assertFalse(
            loginService.login("unknown@example.com", "Password123!")
        );
    }

    @Test
    void shouldAcceptEmailRegardlessOfCase() {
        assertTrue(
            loginService.login("MOUAD@EXAMPLE.COM", "Password123!")
        );
    }

    @Test
    void shouldRejectNullCredentials() {
        assertFalse(loginService.login(null, "Password123!"));
        assertFalse(loginService.login("mouad@example.com", null));
    }
    
    @Test
    void shouldShowSpecificMessageAfterThreeFailedAttempts() {
        loginService.login("mouad@example.com", "wrong1");
        loginService.login("mouad@example.com", "wrong2");
        loginService.login("mouad@example.com", "wrong3");

        assertEquals(
            "Echec de connexion : vous avez atteint 3 tentatives échouées.",
            loginService.getLoginMessage("mouad@example.com")
        );
    }

    @Test
    void shouldResetFailedAttemptsAfterSuccessfulLogin() {
        loginService.login("mouad@example.com", "wrong1");
        loginService.login("mouad@example.com", "wrong2");
        loginService.login("mouad@example.com", "Password123!");

        assertEquals(
            "Email ou mot de passe incorrect.",
            loginService.getLoginMessage("mouad@example.com")
        );
    }
    @Test
    void shouldLogSecurityEventAfterThreeFailedAttempts() {
    Logger logger = Logger.getLogger(LoginService.class.getName());
    List<String> messages = new ArrayList<>();

    Handler handler = new Handler() {
        @Override
        public void publish(LogRecord record) {
            messages.add(record.getMessage());
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() {
        }
    };

    logger.addHandler(handler);

    try {
        loginService.login("mouad@example.com", "wrong1");
        loginService.login("mouad@example.com", "wrong2");
        loginService.login("mouad@example.com", "wrong3");

        assertTrue(messages.stream().anyMatch(
            message -> message.contains("SECURITY_EVENT")
        ));
    } finally {
        logger.removeHandler(handler);
    }
}
}
