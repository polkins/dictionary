package com.example.dictionary.observer.subscribers;

import org.springframework.stereotype.Component;

@Component
public class SmsNotificationImpl implements Notification{
    @Override
    public void send(String message) {
        System.out.println("Sms message: " + message);
    }
}
