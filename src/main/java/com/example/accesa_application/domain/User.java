package com.example.accesa_application.domain;

public class User<ID> extends Entity<ID>{
    private String username;
    private String email;
    private String password;
    private int numberOfQuestsCompleted;

    private int points;

    public User( String username,String email, String password, int numberOfQuestsCompleted, int points) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.numberOfQuestsCompleted = numberOfQuestsCompleted;
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
