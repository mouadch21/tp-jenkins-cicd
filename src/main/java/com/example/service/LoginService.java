package com.example.service;

public class LoginService {

    public boolean authenticate(String username, String password) {
        return "admin".equals(username) && "smartcampus123".equals(password);
    }
}
