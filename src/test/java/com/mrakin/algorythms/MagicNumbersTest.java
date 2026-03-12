package com.mrakin.algorythms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MagicNumbersTest {

    private final MagicNumbers magicNumbers = new MagicNumbers();

    @Test
    void testSingleElement() {
        assertEquals(1, magicNumbers.getMagicElementsCount(new int[]{1}));
    }

    @Test
    void testTwoElements() {
        assertEquals(0, magicNumbers.getMagicElementsCount(new int[]{1, 2}));
        assertEquals(0, magicNumbers.getMagicElementsCount(new int[]{1, 1}));
    }

    @Test
    void testThreeElements() {
        assertEquals(3, magicNumbers.getMagicElementsCount(new int[]{1, 1, 1}));
        assertEquals(1, magicNumbers.getMagicElementsCount(new int[]{2, 1, 1}));
    }

    @Test
    void testFourElements() {
        // [2, 1, 6, 4]
        // remove 2: [1, 6, 4] -> even sum: 1+4=5, odd sum: 6. 5!=6
        // remove 1: [2, 6, 4] -> even sum: 2+4=6, odd sum: 6. 6==6. Count=1
        // remove 6: [2, 1, 4] -> even sum: 2+4=6, odd sum: 1. 6!=1
        // remove 4: [2, 1, 6] -> even sum: 2+6=8, odd sum: 1. 8!=1
        assertEquals(1, magicNumbers.getMagicElementsCount(new int[]{2, 1, 6, 4}));
    }

    @Test
    void testFiveElements() {
        // [1, 1, 1, 1, 1]
        assertEquals(5, magicNumbers.getMagicElementsCount(new int[]{1, 1, 1, 1, 1}));
    }

    @Test
    void testEmptyArray() {
        assertEquals(0, magicNumbers.getMagicElementsCount(new int[]{}));
    }
}
