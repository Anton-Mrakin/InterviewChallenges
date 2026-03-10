package com.mrakin;

import org.graalvm.collections.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class StreamsTests {
    @ParameterizedTest
    @CsvSource({
            "'a,b,a,c,d', 'a'", // Первый дубликат — 'a'
            "'x,y,x,z,z', 'x'", // Первый дубликат — 'x'
            "'1,2,3,4', ''"     // Нет повторений
    })
    void firstRepeat(String input, String expectedFirstDuplicate) {
        List<String> list = Arrays.asList(input.split(","));
        Set<String> seen = new HashSet<>();
        Optional<String> firstDup =
                list.stream()
                        .filter(x -> !seen.add(x))
                        .findFirst();
        Assertions.assertEquals(
                expectedFirstDuplicate.isEmpty() ? Optional.empty() : Optional.of(expectedFirstDuplicate),
                firstDup
        );
    }

    @ParameterizedTest
    @CsvSource({
            "'1,2,3,4,5', 15",  // Сумма чисел: 1+2+3+4+5 = 15
            "'6,7,8,9', 30",    // Сумма чисел: 6+7+8+9 = 30
            "'10', 10"          // Единственный элемент
    })
    void sum(String input, int expectedSum) {
        List<Integer> nums = Arrays.stream(input.split(","))
                .map(Integer::parseInt)
                .toList();
        int result = nums.stream()
                .mapToInt(Integer::intValue)
                .sum();
        Assertions.assertEquals(expectedSum, result);
    }

    @ParameterizedTest
    @CsvSource({
            "'a,b,c,d', 'a-b,b-c,c-d'", // Пары: (a, b), (c, d)
            "'1,2,3', '1-2,2-3'",   // Пары: (1, 2), (2, 3)
            "'x', ''"              // Один элемент — ничего не создаётся
    })
    void slidingWindow(String input, String expectedPairs) {
        List<String> list = Arrays.asList(input.split(","));
        List<Pair<String, String>> pairs =
                IntStream.range(0, list.size() - 1)
                        .mapToObj(i -> Pair.create(list.get(i), list.get(i + 1)))
                        .toList();
        String result = pairs.stream()
                .map(pair -> pair.getLeft() + "-" + pair.getRight())
                .collect(Collectors.joining(","));
        Assertions.assertEquals(expectedPairs, result);
    }

    @ParameterizedTest
    @CsvSource({
            "'1,2,3,4', 'evens:2,4 odds:1,3'",
            "'5,6,7', 'evens:6 odds:5,7'",
            "'0,8', 'evens:0,8 odds:'"
    })
    void partitioningBy(String input, String expectedOutput) {
        List<Integer> nums = Arrays.stream(input.split(","))
                .map(Integer::parseInt)
                .toList();
        Map<Boolean, List<Integer>> parts =
                nums.stream()
                        .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        String result = "evens:" + parts.get(true).stream().map(String::valueOf).collect(Collectors.joining(","))
                + " odds:" + parts.get(false).stream().map(String::valueOf).collect(Collectors.joining(","));
        Assertions.assertEquals(expectedOutput, result);
    }

    @ParameterizedTest
    @CsvSource({
            "'a,b,a,c,d', 4",
            "'x,y,x,z', 3",
            "'1,2,3,4,5', 5"
    })
    void collectingAndThen(String input, int expectedSize) {
        List<String> list = Arrays.asList(input.split(","));
        Set<String> unmodifiable =
                list.stream()
                        .collect(
                                Collectors.collectingAndThen(
                                        Collectors.toSet(),
                                        Collections::unmodifiableSet
                                ));
        Assertions.assertEquals(expectedSize, unmodifiable.size());
    }

    @ParameterizedTest
    @CsvSource({
            "'10,20,30,40', 40",
            "'1,2,3', 3",
            "'100,200', 200"
    })
    void max(String input, int expectedMax) {
        List<String> nums = Arrays.asList(input.split(","));

        Optional<Integer> max =
                nums.stream()
                        .map(Integer::parseInt)
                        .max(Comparator.naturalOrder());
        Assertions.assertEquals(expectedMax, max.orElseThrow());
    }
}