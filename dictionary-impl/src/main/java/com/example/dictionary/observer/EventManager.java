package com.example.dictionary.observer;

import com.example.dictionary.observer.subscribers.Notification;

public interface EventManager {
    void subscribe(Notification notification);
    void unsubscribe(Notification notification);
    void notify(String message);
}
