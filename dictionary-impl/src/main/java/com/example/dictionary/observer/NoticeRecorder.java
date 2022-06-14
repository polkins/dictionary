package com.example.dictionary.observer;

public interface NoticeRecorder {
    void register();

    void notify(String message);
}
