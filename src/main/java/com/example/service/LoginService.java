
package com.example.service;

import java.util.HashMap;
import java.util.Map;

import com.example.model.User;

public class LoginService {

    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Integer> failedAttempts = new HashMap<>();

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

        failedAttempts.merge(normalizedEmail, 1, Integer::sum);
        return false;
    }

    public String getLoginMessage(String email) {
        if (email == null) {
            return "Veuillez saisir votre email et votre mot de passe.";
        }

        String normalizedEmail = email.trim().toLowerCase();
        int attempts = failedAttempts.getOrDefault(normalizedEmail, 0);

        if (attempts >= 3) {
            return "Échec de connexion : vous avez atteint 3 tentatives échouées.";
        }

        return "Email ou mot de passe incorrect.";
    }
}