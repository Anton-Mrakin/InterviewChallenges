package com.mrakin.structures.hashmap;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubArraySumTest {

    private final SubArraySum solver = new SubArraySum();

    @ParameterizedTest(name = "nums={0}, k={1}, expected={2}")
    @CsvSource({
            "'1,1,1', 2, true",
            "'1,2,3', 3, true",
            "'1,2,3', 7, false",
            "'-1,-1,1', 0, true",
            "'1,2,3', 0, false",
            "'0,0,0', 0, true",
            "'1,2,3,4,5', 15, true",
            " , 5, false"
    })
    void testHasSubarraySum(String numsStr, int k, boolean expected) {
        int[] nums = parseArray(numsStr);
        assertEquals(expected, solver.hasSubarraySum(nums, k));
    }

    @ParameterizedTest(name = "nums={0}, k={1}, expected={2}")
    @CsvSource({
            "'1,1,1', 2, 2",
            "'1,2,3', 3, 2", // [1,2] and [3]
            "'1,-1,0', 0, 3", // [1,-1], [0], [1,-1,0]
            "'1,2,3', 7, 0",
            "'0,0,0,0', 0, 10", // 4*5/2 = 10
            "'1,2,3,4,5', 15, 1",
            " , 5, 0"
    })
    void testCountSubarraysWithSum(String numsStr, int k, int expected) {
        int[] nums = parseArray(numsStr);
        assertEquals(expected, solver.countSubarraysWithSum(nums, k));
    }

    private int[] parseArray(String s) {
        if (s == null || s.trim().isEmpty() || s.equals("null")) {
            return null;
        }
        return Arrays.stream(s.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
