package com.example.hw07a;

public class ChatClass {
    String messageId;
    String userId;
    String message;

    public ChatClass() {
    }

    public ChatClass(String messageId, String userId, String message) {
        this.messageId = messageId;
        this.userId = userId;
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
