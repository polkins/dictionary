package com.example.dictionary;

import com.example.dictionary.observer.EventManagerImpl;
import com.example.dictionary.observer.NoticeRecorderImpl;
import com.example.dictionary.observer.subscribers.EmailNotificationImpl;
import com.example.dictionary.observer.subscribers.Notification;
import com.example.dictionary.observer.subscribers.PushNotificationImpl;
import com.example.dictionary.observer.subscribers.SmsNotificationImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ObserverTests {

    @InjectMocks
    private NoticeRecorderImpl noticeRecorder;

    @Spy
    private EmailNotificationImpl emailNotificationSpy;

    @Spy
    private SmsNotificationImpl smsNotificationSpy;

    @Spy
    private PushNotificationImpl pushNotificationSpy;

    @Spy
    private EventManagerImpl eventManagerSpy;

    @Test
    public void notify_message_notifyMessage() {
        final String message = "Hello";

        noticeRecorder.notify(message);

        Mockito.verify(eventManagerSpy).notify(message);
    }

    @Test
    public void register_void_subscribeEmailAndSmsAndPushNotifications() {
        noticeRecorder.register();

        Mockito.verify(eventManagerSpy, Mockito.times(3)).subscribe(Mockito.any(Notification.class));
    }

    @Test
    public void notify_message_invokeSendSubscriber() {
        final String message = "Hello";

        eventManagerSpy.subscribe(emailNotificationSpy);
        eventManagerSpy.subscribe(smsNotificationSpy);
        eventManagerSpy.subscribe(pushNotificationSpy);

        eventManagerSpy.notify(message);

        Mockito.verify(emailNotificationSpy).send(message);
        Mockito.verify(smsNotificationSpy).send(message);
        Mockito.verify(pushNotificationSpy).send(message);
    }
}
