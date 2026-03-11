package com.mrakin.structures.prioqueue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MonotonicWindowTest {

    private final MonotonicWindow solver = new MonotonicWindow();

    @ParameterizedTest(name = "nums={0}, k={1}, expected={2}")
    @CsvSource({
            "'1,3,-1,-3,5,3,6,7', 3, '3,3,5,5,6,7'",
            "'1', 1, '1'",
            "'1,3,-1', 3, '3'",
            "'2,2,2,2', 2, '2,2,2'",
            "'1,2,3,4', 2, '2,3,4'",
            "'4,3,2,1', 2, '4,3,2'",
            " , 0, " // Пустой ввод
    })
    void testMaxSlidingWindow(String numsStr, int k, String expectedStr) {
        int[] nums = parseArray(numsStr);
        int[] expected = parseArray(expectedStr);

        assertArrayEquals(expected, solver.maxSlidingWindow(nums, k));
    }

    private int[] parseArray(String s) {
        if (s == null || s.trim().isEmpty()) {
            return new int[0];
        }
        return Arrays.stream(s.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}