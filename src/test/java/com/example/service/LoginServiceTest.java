
package com.example.service;

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
            "Échec de connexion : vous avez atteint 3 tentatives échouées.",
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

}
