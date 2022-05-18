package com.example.dictionary;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AlgorithmTests {
    @Test
    public void Test1() {
        // Подсчет самых повторяющихся символов
        String str = "aabbcdeeeff";

        long startTime = System.nanoTime();

        var map = str
                .chars()
                .mapToObj(i -> (char) i)
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));

        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;

        // 2209300
        System.out.println(totalTime);

        var max = map.values()
                .stream()
                .max(Long::compare)
                .orElse(0L);

        var maxEntry = map.entrySet()
                .stream()
                .filter(e -> Objects.equals(e.getValue(), max))
                .findFirst()
                .orElseThrow();

        assertThat(maxEntry.getKey()).isEqualTo('e');
        assertThat(maxEntry.getValue()).isEqualTo(3L);
    }

    @Test
    public void Test1_Solution() {
        // Подсчет самых повторяющихся символов
        String str = "aabbcdeeeff";

        long startTime = System.nanoTime();

        Map<Character, Integer> result = new HashMap<>();
        // либо использовать for(char ch: str.toCharArray()) { ... }
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            result.compute(ch, (k, v) -> (v == null) ? 1 : ++v);
        }

        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;

        // 323000
        System.out.println(totalTime);

        var max = result.values()
                .stream()
                .max(Long::compare)
                .orElse(0);

        var maxEntry = result.entrySet()
                .stream()
                .filter(e -> Objects.equals(e.getValue(), max))
                .findFirst()
                .orElseThrow();

        assertThat(maxEntry.getKey()).isEqualTo('e');
        assertThat(maxEntry.getValue()).isEqualTo(3L);
    }

    @Test
    public void Test2() {
        // Отыскать первый неповторяющийся символ
        String str = "aabbcdeeeff";

        long startTime = System.nanoTime();

        var map = str
                .chars()
                .mapToObj(i -> (char) i)
                .collect(Collectors.groupingBy(i -> i, LinkedHashMap::new, Collectors.counting()));

        var minEntry = map.entrySet()
                .stream()
                .filter(e -> e.getValue() == 1)
                .findFirst()
                .orElseThrow();

        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;

        // 2359400
        System.out.println(totalTime);

        assertThat(minEntry.getKey()).isEqualTo('c');
        assertThat(minEntry.getValue()).isEqualTo(1L);
    }

    @Test
    public void Test2_Solution() {
        // Отыскать первый неповторяющийся символ
        String str = "aabbcdeeeff";

        long startTime = System.nanoTime();

        Map<Character, Integer> chars = new LinkedHashMap<>();
        // либо использовать for(char ch: str.toCharArray()) { ... }
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            chars.compute(ch, (k, v) -> (v == null) ? 1 : ++v);
        }

        var ch = firstNonRepeatedCharacter(chars);

        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;

        // 356200
        System.out.println(totalTime);

        assertThat(ch).isEqualTo('c');
    }

    private char firstNonRepeatedCharacter(Map<Character, Integer> chars) {
        for (Map.Entry<Character, Integer> entry : chars.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }
        return Character.MIN_VALUE;
    }
}
