package com.example.moviesubmission2.Notification;

public class NotificationItem {
    private int id;
    private String sender;
    private String message;

    public NotificationItem(int id, String sender, String message) {
        this.id = id;
        this.sender = sender;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }
}

