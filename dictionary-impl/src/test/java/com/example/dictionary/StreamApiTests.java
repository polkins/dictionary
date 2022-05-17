package com.example.dictionary;

import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StreamApiTests {
    public record Order111(long orderId, double amount) {
    }

    public record Item(String name, double price) {
    }

    class Order {
        long orderId;
        double amount;

        public Order(long orderId, double amount) {
            this.orderId = orderId;
            this.amount = amount;
        }

        public String toString() {
            return orderId + ", " + amount;
        }
    }

    @Test
    public void Test_11_1() {
//        Что будет результатом? (выбрать один)
//        a) 70.0 50.0
//        b) 50.0 70.0 50.0
//        c) 1, 50.0 5, 70.0 7, 50.0
//        d) 5, 70.0 7, 50.0
//        e) ничего не будет выведено
        LinkedList<Double> result = new LinkedList<>();

        List<Order111> orders = List.of(
                new Order111(1, 50),
                new Order111(5, 70),
                new Order111(7, 50));
        orders.stream()
                .collect(Collectors.groupingBy(Order111::amount))
//                .forEach((source, r) -> System.out.print(source + " "));
                .forEach((source, r) -> result.add(source));

        // a) 70.0 50.0
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).isEqualTo(70);
        assertThat(result.get(1)).isEqualTo(50);
    }

    @Test
    public void Test_11_2() {
//        Что будет результатом? (выбрать один)
//        a) 4, 0.0
//        b) 4, 190.0
//        c) 17, 0.0
//        d) 17, 190.0
        List<Order> orders = Arrays.asList(
                new Order(1, 50),
                new Order(5, 70),
                new Order(7, 70));
        Order order = orders.stream()
                .reduce(new Order(4, 0), (p1, p2) ->
                        new Order(p1.orderId, p1.amount += p2.amount));

//        System.out.print(order);

        // b) 4, 190.0
        assertThat(order.toString()).isEqualTo("4, 190.0");
    }

    @Test
    public void Test_11_3() {
//        Что будет результатом? (выбрать один)
//        a) ничего не будет выведено
//        b) 5, 70.0
//        c) 1, 50.0
//        d) 7, 70.0

        List<Order> orders = Arrays.asList(
                new Order(1, 50),
                new Order(5, 70),
                new Order(7, 70));
        var order = orders.stream()
                .reduce((p1, p2) -> p1.amount > p2.amount ? p1 : p2);
//                .ifPresent(System.out::println);

        // d) 7, 70.0
        assertThat(order.get().toString()).isEqualTo("7, 70.0");
    }

    @Test
    public void Test_11_4() {
//        Что будет результатом? (выбрать один)
//        a) compilation fails
//        b) 0.0
//        c) 15.0
//        d) 20.0

        List<Item> items = List.of(
                new Item("Jeans", 20),
                new Item("Socks", 10),
                new Item("Jacket", 30));
        var value = items.stream()
                .filter(s -> s.name().endsWith("s"))
                .mapToDouble(Item::price)
                .average()
                .getAsDouble();
//        System.out.print(value);

        // c) 15.0
        assertThat(value).isEqualTo(15);
    }

    @Test
    public void Test_11_5() {
//        Что будет результатом? (выбрать один)
//        a) [4, 3]
//        b) [1, 3]
//        c) [2, 4]
//        d) [2, 4]
//        e) runtime error

        Map<String, Integer> map = new HashMap<>();
        map.compute("y", (k, v) -> v == null ? 1 : 0);
        map.compute("z", (k, v) -> v == null ? 2 : 0);
        map.computeIfPresent("z", (k, v) -> v != null ? 3 : 0);
        map.computeIfAbsent("y", v -> v != null ? 4 : 0);
        System.out.println(map.values());

        var values = map.values().stream().toList();

        // b) [1, 3]
        assertThat(values.get(0)).isEqualTo(1);
        assertThat(values.get(1)).isEqualTo(3);
    }

    @Test
    public void Test_11_6() {
//        Какой фрагмент кода следует вставить вместо комментария line 1, чтобы
//        в консоль было выведено значение true? (выбрать один)
//        a) noneMatch(i -> i == 2);
//        b) allMatch(i -> i == 2);
//        c) anyMatch(i -> i == 2);
//        d) findFirst().

        List<Integer> integers = List.of(1, 2, 3, 1, 7);
        boolean res = integers.stream()
                .// line 1
                        anyMatch(i -> i == 2);

//        System.out.print(res);

        assertThat(res).isTrue();
    }
}
