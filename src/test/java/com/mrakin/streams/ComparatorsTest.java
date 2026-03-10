package com.mrakin.streams;

import com.mrakin.streams.data.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class ComparatorsTest {
    private final Comparators comparators = new Comparators();

    List<User> sampleUsers = List.of(
        new User(1L, "Alice", "New York"),
        new User(2L, "Bob", "Los Angeles"),
        new User(3L, "Alice", "San Francisco"),
        new User(4L, "Charlie", "New York")
    );

    @Test
    public void testCompareByName() {
        List<User> result = comparators.compareByName(sampleUsers);

        List<User> expected = List.of(
            new User(1L, "Alice", "New York"),
            new User(3L, "Alice", "San Francisco"),
            new User(2L, "Bob", "Los Angeles"),
            new User(4L, "Charlie", "New York")
        );

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testCompareByNameReversed() {
        List<User> result = comparators.compareByNameReversed(sampleUsers);

        List<User> expected = List.of(
            new User(4L, "Charlie", "New York"),
            new User(2L, "Bob", "Los Angeles"),
            new User(1L, "Alice", "New York"),
            new User(3L, "Alice", "San Francisco")
        );

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testCompareByNameThenId() {
        List<User> result = comparators.compareByNameThenId(sampleUsers);

        List<User> expected = List.of(
            new User(1L, "Alice", "New York"),
            new User(3L, "Alice", "San Francisco"),
            new User(2L, "Bob", "Los Angeles"),
            new User(4L, "Charlie", "New York")
        );

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testGroupingBy() {
        Map<String, List<User>> result = comparators.groupingBy(sampleUsers);

        Map<String, List<User>> expected = Map.of(
            "Alice", List.of(
                new User(1L, "Alice", "New York"),
                new User(3L, "Alice", "San Francisco")
            ),
            "Bob", List.of(new User(2L, "Bob", "Los Angeles")),
            "Charlie", List.of(new User(4L, "Charlie", "New York"))
        );

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testCountingBy() {
        Map<String, Long> result = comparators.countingBy(sampleUsers);

        Map<String, Long> expected = Map.of(
            "Alice", 2L,
            "Bob", 1L,
            "Charlie", 1L
        );

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testMapping() {
        Map<String, String> result = comparators.mapping(sampleUsers);

        Map<String, String> expected = Map.of(
            "New York", "AliceCharlie",
            "Los Angeles", "Bob",
            "San Francisco", "Alice"
        );

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testReduce() {
        List<Integer> integers = List.of(1, 2, 3, 4, 5);
        Integer result = comparators.reduce(integers);

        Assertions.assertEquals(15, result);
    }

    @Test
    public void testReduceEmptyList() {
        List<Integer> integers = List.of();
        Integer result = comparators.reduce(integers);

        Assertions.assertEquals(0, result);
    }
}