package com.example.accesa_application.domain;

public class Quest<ID> extends Entity<ID>{
    private String name;
    private String description;
    private int points;
    private ID userId;
    private boolean isCompleted;

    public Quest(ID userId,String name, String description, int points, boolean isCompleted) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.points = points;
        this.isCompleted = isCompleted;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    public ID getUserId() {
        return userId;
    }
    public void setUserId(ID userId) {
        this.userId = userId;
    }

}
