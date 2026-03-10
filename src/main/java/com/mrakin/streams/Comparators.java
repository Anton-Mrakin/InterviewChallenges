package com.mrakin.streams;

import com.mrakin.streams.data.User;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Comparators {
    public List<User> compareByName(List<User> users) {
        return users.stream()
                .sorted(Comparator.comparing(User::name))
                .toList();
    }

    public List<User> compareByNameReversed(List<User> users) {
        return users.stream()
                .sorted(Comparator.comparing(User::name).reversed())
                .toList();
    }

    public List<User> compareByNameThenId(List<User> users) {
        return users.stream()
                .sorted(Comparator.comparing(
                        User::name
                                , Comparator.nullsLast(String::compareTo)
                        )
                        .thenComparing(User::id))
                .toList();
    }

    public Map<String, List<User>> groupingBy(List<User> users) {
        return users.stream()
                .collect(Collectors.groupingBy(User::name));
    }

    public Map<String, Long> countingBy(List<User> users) {
        return users.stream()
                .collect(Collectors.groupingBy(
                        User::name
                        , Collectors.counting()));
    }

    public Map<String, String> mapping(List<User> users) {
        return users.stream()
                .collect(Collectors.groupingBy(
                        User::city,
                        Collectors.mapping(
                                User::name
                                , Collectors.joining())
                ));
    }

    public Integer reduce(List<Integer> integer) {
        return integer.stream().reduce(0, Integer::sum);
    }
}
