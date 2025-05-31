package com.example.yourtherapy.utils;

public class MoodEntry {
    private Integer moodEnum;
    private String title;
    private String description;
    private String created_at;
    private String user;

    public MoodEntry(Integer moodEnum, String title, String description, String created_at, String user) {
        this.moodEnum = moodEnum;
        this.title = title;
        this.description = description;
        this.created_at = created_at;
        this.user = user;
    }

    public Integer getMoodEnum() {
        return moodEnum;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public String getUser() {
        return user;
    }
}

