package com.example.dictionary.observer.subscribers;

import org.springframework.stereotype.Component;

@Component
public class EmailNotificationImpl implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Email message: " + message);
    }
}
