package com.example.dictionary.service;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class IncreaseIdServiceImpl implements IncreaseIdService {
    @Override
    public void increaseId(Supplier<Long> supplier, Consumer<Long> consumer) {
        var value = supplier.get() + 5;
        consumer.accept(value);
    }
}
