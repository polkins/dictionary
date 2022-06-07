package com.example.dictionary;

import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StringTests {
    @Test
    public void test_8_1() {
//        Что будет результатом компиляции и запуска? (выбрать один)
//        a) Hello
//        b) Hellonull
//        c) HelloHello
//        d) runtime error

        String str = "Hello";
//        System.out.print(str);
        assertThat(str).isEqualTo("Hello");
        doWork(str);
//        System.out.print(str);
        assertThat(str).isEqualTo("Hello");

        // a) Hello - String неизменяемый тип данных
    }

    static void doWork(String value) {
        value = null;
    }

    @Test
    public void test_8_2() {
//        В результате при компиляции и запуске будет выведено (выбрать один):
//        a) 12345
//        b) 3345
//        c) 1239
//        d) 339
//        e) 15
//        f) compilation fails

        String s = new String("3");
//        System.out.println(1 + 2 + s + 4 + 5);
        var result = 1 + 2 + s + 4 + 5;

        assertThat(result).isEqualTo("3345");

        // a) 12345
        // ПРАВИЛЬНЫЙ ОТВЕТ b) 3345 - до s работал как с числами, а после как со строками
    }

    @Test
    public void test_8_3() {
//        Какие из предложенных операторов дадут результат true? (выбрать два)
//        a) s1 == s2
//        b) s1 == s3
//        c) s2 == s4
//        d) s2 == s3
//        e) s2.equals(s1)

        String s1 = new String("Java");
        String s2 = "Java";
        String s3 = new String(s1);
        String s4 = "Java";

        assertThat(s1 == s2).isFalse();
        assertThat(s1 == s3).isFalse();
        assertThat(s2 == s4).isTrue();
        assertThat(s2 == s3).isFalse();
        assertThat(s2.equals(s1)).isTrue();

        // c) s2 == s4 - s2 и s4 ссылаются на один и тот же объект
        // e) s2.equals(s1) - s2 и s1 не ссылаются на один объект, но equals еще проверит по значению
    }

    @Test
    public void test_8_4() {
//        Что будет выведено на консоль при компиляции и выполнении следующих
//        строчек кода? (выбрать один)
//        a) [a, b, c]
//        b) [a0, b1, c2]
//        c) [a1, b2, c3]
//        d) compilation fails

        String[] strings = new String[]{"a", "b", "c"};
        int k = 0;
        for (String element : strings) {
            strings[k].concat(String.valueOf(k));
            ++k;
        }
//        System.out.print(Arrays.toString(strings));
        assertThat(strings[0]).isEqualTo("a");
        assertThat(strings[1]).isEqualTo("b");
        assertThat(strings[2]).isEqualTo("c");

        // a) [a, b, c] - concat не меняет текущую строку, а создает новую, которая никуда не присваивается
    }

    @Test
    public void test_8_5() {
//        Какой из фрагментов кода, будучи вставленным вместо комментария 1 и 2
//        соответственно, выведет в консоль 01ab? (выбрать один)
//        a) st = st.concat("1");sb.append("b");
//        b) st.concat("1");
//        c) sb.append("b");
//        d) st = st.concat("1");
//        e) sb.concat("b");
//        f) st = st.append("1");
//        g) sb.append("b");

        String st = "0";
        StringBuffer sb = new StringBuffer("a");
// 1
// 2
//        System.out.print(st);
//        System.out.print(sb);
        st = st.concat("1");
        sb.append("b");
        assertThat(st + sb).isEqualTo("01ab");

        // a) st = st.concat("1");sb.append("b"); - с помощью concat создается новая строка и присваивается в st,
        // а с помощью append изменяется текущий экземпляр sb и добавляется "b"
    }
}
