package com.example.dictionary.observer.subscribers;

import org.springframework.stereotype.Component;

@Component
public class PushNotificationImpl implements Notification{
    @Override
    public void send(String message) {
        System.out.println("Push message: " + message);
    }
}
