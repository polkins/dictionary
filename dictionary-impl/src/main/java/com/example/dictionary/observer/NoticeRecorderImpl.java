package com.example.dictionary.observer;

import com.example.dictionary.observer.subscribers.EmailNotificationImpl;
import com.example.dictionary.observer.subscribers.PushNotificationImpl;
import com.example.dictionary.observer.subscribers.SmsNotificationImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeRecorderImpl implements NoticeRecorder{
    private final EventManager eventManager;
    private final EmailNotificationImpl emailNotification;
    private final SmsNotificationImpl smsNotification;
    private final PushNotificationImpl pushNotification;

    public void register() {
        eventManager.subscribe(emailNotification);
        eventManager.subscribe(smsNotification);
        eventManager.subscribe(pushNotification);
    }

    public void notify(String message) {
        eventManager.notify(message);
    }
}
