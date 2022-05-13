package com.example.dictionary;

import com.example.dictionary.api.dto.DictionaryAccountDto;
import com.example.dictionary.api.dto.DictionaryBankDto;
import com.example.dictionary.collectors.AccountBalanceSumCollector;
import com.example.dictionary.service.IncreaseIdServiceImpl;
import org.testng.annotations.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FunctionalInterfaceTests {
    @Test
    public void Test_7_1() {
//        Какое значение примет result? (выбрать один)
//        a) 0
//        b) 2
//        c) 3
//        d) compilation fails at line 1

        long result = Arrays.stream(new String[]{"JSE", "JDK", "J"})
                .filter(s -> s.length() > 1)
                .filter(s -> s.contains("J"))
                .count();

        // b) 2
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void Test_7_2() {
//        Что будет выведено в консоль?
//        a) 0 JDK
//        b) 0 0 JDK
//        c) 0 1 JDK
//        d) 0 0 0 0 0 JDK
//        e) 0 1 2 3 4 JDK

        Predicate<String> predicate = s -> {
            int i = 0;
            boolean result = s.contains("JDK");
            System.out.print(i++ + " ");
            return result;
        };

        Arrays.stream(new String[]{"JRE", "JDK", "JVM", "JSE", "JDK"})
                .filter(predicate)
                .findFirst().ifPresent(System.out::print);

        // d) 0 0 0 0 0 JDK
        // ПРАВИЛЬНЫЙ ОТВЕТ b) 0 0 JDK
    }

    @Test
    public void Test_7_3() {
//        Какой оператор должен быть вставлен вместо line 1, чтобы был найден максимальный целый элемент потока? (выбрать два)
//        a) numbers.max(Integer::max).get()
//        b) numbers.max()
//        c) numbers.max(Comparator.comparing(n -> n)).get()
//        d) numbers.boxed().max(Comparator.comparing(n -> n)).get()
//        e) numbers.max(Comparator.comparing(n -> n))

        IntStream numbers = new Random().ints(10, 0, 20);
        System.out.print(
                //line 1
                numbers.max()
        );

        System.out.print(
                //line 1
                numbers.boxed().max(Comparator.comparing(n -> n)).get()
        );

        // a, b
        // ПРАВИЛЬНЫЙ ОТВЕТ b, d
    }

    @Test
    public void Test_7_4() {
//        Что будет выведено в результате выполнения фрагмента кода? (выбрать один)
//        a) 14
//        b) 11
//        c) 12
//        d) 24
//        e) 22

        LinkedList<Integer> peekResult = new LinkedList<>();
        Stream.of(1).peek(
                        ((Consumer<Integer>) (i -> i += 1))
                                .andThen(i -> i += 2))
//                .forEach(System.out::print);
                .forEach(peekResult::add);
        LinkedList<Integer> mapResult = new LinkedList<>();
        Stream.of(1).map(
                        ((Function<Integer, Integer>) (i -> i += 1))
                                .andThen(i -> i += 2))
//                .forEach(System.out::print);
                .forEach(mapResult::add);

        // a) 14
        assertThat(peekResult.get(0)).isEqualTo(1);
        assertThat(mapResult.get(0)).isEqualTo(4);
    }

    @Test
    public void Test_7_5() {
//        Какой оператор должен быть вставлен вместо line 1, чтобы было определено
//        число строк с длиной меньше чем 4? (выбрать один)
//        a) strings.peek(x -> x.length() <= 4).count().get()
//        b) strings.filter(x -> x.length() <= 4).count()
//        c) strings.filter(x -> x.length() <= 4).mapToInt(Integer::valueOf).count()
//        d) strings.map(x -> x.length() <= 4).count()

        Stream<String> strings = Arrays.stream(
                new String[]{"Java", "Standard", "Edition", "version", "14"});

        var result = strings.filter(x -> x.length() <= 4).count();
        System.out.print(
                // line n1
                result
        );

        //        b) strings.filter(x -> x.length() <= 4).count()
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void getAccountBalanceSumUsingCollector(){
        List<DictionaryAccountDto> accounts = List.of(
                new DictionaryAccountDto(1L, "1111", 1L, 1L, 150.0),
                new DictionaryAccountDto(2L, "2222", 1L, 1L, 50.0),
                new DictionaryAccountDto(3L, "3333", 1L, 1L, 100.0),
                new DictionaryAccountDto(4L, "4444", 1L, 1L, 250.0),
                new DictionaryAccountDto(5L, "5555", 1L, 1L, 70.0));

        var result = accounts.stream().collect(new AccountBalanceSumCollector());

        assertThat(result).isEqualTo(620.0);
    }

    @Test
    public void increaseAccountId(){
        var accountDto = new DictionaryAccountDto(1L, "1111", 1L, null, 150.0);
        var increaseIdService = new IncreaseIdServiceImpl();

        increaseIdService.increaseId(accountDto::getId, accountDto::setBankId);

        assertThat(accountDto.getBankId()).isEqualTo(6);
    }

    @Test
    public void increaseBankId(){
        var bankDto = new DictionaryBankDto(4L, "Бабанк", "г.Бобруйск", "88005553535");
        var increaseIdService = new IncreaseIdServiceImpl();

        increaseIdService.increaseId(bankDto::getId, bankDto::setId);

        assertThat(bankDto.getId()).isEqualTo(9L);
    }
}
