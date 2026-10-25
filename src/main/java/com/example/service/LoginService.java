
package com.example.service;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.example.model.User;

public class LoginService {

    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Integer> failedAttempts = new HashMap<>();
    private static final Logger LOGGER =
        Logger.getLogger(LoginService.class.getName());

    public void register(User user) {
        if (user == null || user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException(
                "L'utilisateur et son email sont obligatoires."
            );
        }

        users.put(user.getEmail().trim().toLowerCase(), user);
    }

    public boolean login(String email, String password) {
        if (email == null || password == null) {
            return false;
        }

        String normalizedEmail = email.trim().toLowerCase();
        User user = users.get(normalizedEmail);

        if (user != null && password.equals(user.getPassword())) {
            failedAttempts.remove(normalizedEmail);
            return true;
        }

        int attempts = failedAttempts.merge(
             normalizedEmail, 1, Integer::sum
        );

        if (attempts == 3) {
          LOGGER.log(
            java.util.logging.Level.WARNING,
            "SECURITY_EVENT: Three consecutive login failures for email: {0}",
            normalizedEmail
            );
        } 

        return false;
    }

    public String getLoginMessage(String email) {
        if (email == null) {
            return "Veuillez saisir votre email et votre mot de passe.";
        }

        String normalizedEmail = email.trim().toLowerCase();
        int attempts = failedAttempts.getOrDefault(normalizedEmail, 0);

        if (attempts >= 3) {
            return "Echec de connexion : vous avez atteint 3 tentatives échouées.";
        }

        return "Email ou mot de passe incorrect.";
    }
}