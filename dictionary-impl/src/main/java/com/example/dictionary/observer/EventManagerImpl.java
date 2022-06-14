package com.example.dictionary.observer;

import com.example.dictionary.observer.subscribers.Notification;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class EventManagerImpl implements EventManager {
    private final HashSet<Notification> notifications = new HashSet<>();

    @Override
    public void subscribe(Notification notification) {
        notifications.add(notification);
    }

    @Override
    public void unsubscribe(Notification notification) {
        notifications.remove(notification);
    }

    @Override
    public void notify(String message) {
        for (Notification notification : notifications) {
            notification.send(message);
        }
    }
}
