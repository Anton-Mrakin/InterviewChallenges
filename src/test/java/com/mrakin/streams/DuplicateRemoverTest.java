package com.mrakin.streams;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

class DuplicateRemoverTest {
    private DuplicateRemover duplicateRemover = new DuplicateRemover();

    @ParameterizedTest
    @CsvSource({
            "'a,b,a,c,d', 'a'", // Ожидается, что 'a' будет найден как дубликат
            "'x,y,x,z', 'x'",   // Дубликат 'x'
            "'1,2,3,4', ''"     // Нет дубликатов
    })
    void test(String input, String expectedDuplicates) {
        List<String> inputStream = Arrays.stream(input.split(",")).toList();
        Set<String> duplicates =
                duplicateRemover.removeDuplicates(inputStream);
        Set<String> expected = Set.of(expectedDuplicates.split(",")).stream()
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());

        Assertions.assertEquals(expected, duplicates);
    }
}