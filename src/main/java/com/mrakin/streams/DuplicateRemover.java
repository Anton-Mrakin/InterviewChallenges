package com.mrakin.streams;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DuplicateRemover {
    public Set<String> removeDuplicates(List<String> inputStream) {
        return inputStream.stream()
                .collect(
                        Collectors.groupingBy(
                                Function.identity()
                                , Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}
