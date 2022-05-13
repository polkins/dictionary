package com.example.dictionary.collectors;

import com.example.dictionary.api.dto.DictionaryAccountDto;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class AccountBalanceSumCollector implements Collector<DictionaryAccountDto, HashSet<DictionaryAccountDto>, Double> {
    @Override
    public Supplier<HashSet<DictionaryAccountDto>> supplier() {
        return HashSet::new;
    }

    @Override
    public BiConsumer<HashSet<DictionaryAccountDto>, DictionaryAccountDto> accumulator() {
        return HashSet::add;
    }

    @Override
    public BinaryOperator<HashSet<DictionaryAccountDto>> combiner() {
        return (l, r) -> {l.addAll(r); return l;};
    }

    @Override
    public Function<HashSet<DictionaryAccountDto>, Double> finisher() {
        return s -> s.stream()
                .mapToDouble(DictionaryAccountDto::getBalance)
                .sum();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.CONCURRENT);
    }
}
