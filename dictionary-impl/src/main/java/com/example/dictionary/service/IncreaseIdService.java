package com.example.dictionary.service;

import java.util.function.Consumer;
import java.util.function.Supplier;

@FunctionalInterface
public interface IncreaseIdService {
    void increaseId(Supplier<Long> get, Consumer<Long> set);
}
