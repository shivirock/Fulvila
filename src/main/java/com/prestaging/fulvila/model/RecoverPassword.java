package com.prestaging.fulvila.model;

import org.springframework.stereotype.Component;

@Component
public class RecoverPassword {
    private String email;
    private String newPassword;
    private String token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "RecoverPassword{" +
                "email='" + email + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
