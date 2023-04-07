package com.example.accesa_application.domain;

public class User {
    private String email;
    private String password;
    private int numberOfQuestsCompleted;

    public User(String email, String password, int numberOfQuestsCompleted) {
        this.email = email;
        this.password = password;
        this.numberOfQuestsCompleted = numberOfQuestsCompleted;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNumberOfQuestsCompleted() {
        return numberOfQuestsCompleted;
    }

    public void setNumberOfQuestsCompleted(int numberOfQuestsCompleted) {
        this.numberOfQuestsCompleted = numberOfQuestsCompleted;
    }
}
