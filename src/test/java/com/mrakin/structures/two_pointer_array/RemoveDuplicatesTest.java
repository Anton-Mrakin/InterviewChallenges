package com.mrakin.structures.two_pointer_array;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class RemoveDuplicatesTest {

    private final RemoveDuplicates solver = new RemoveDuplicates();

    @ParameterizedTest(name = "nums={0}, expected={1}")
    @CsvSource({
            "'1,1,2', '1,2'",
            "'0,0,1,1,1,2,2,3,3,4', '0,1,2,3,4'",
            "'1,2,3', '1,2,3'",
            "'1,1,1', '1'",
            "'', ''",
            " , ''"
    })
    void testRemoveDuplicates(String numsStr, String expectedStr) {
        int[] nums = parseArray(numsStr);
        int[] expected = parseArray(expectedStr);

        assertArrayEquals(expected, solver.removeDuplicates(nums));
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
