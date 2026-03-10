package com.mrakin.streams;

import com.mrakin.streams.data.User;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OldesUserInTown {
    public Map<String, User> findOldestUser(List<User> users) {
        return users.stream().collect(
                Collectors.groupingBy(
                        User::city
                        , Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingLong(User::id))
                                , Optional::get
                        ))
        );
    }

    public Map<String, User> alternative(List<User> users) {
        return users.stream().collect(
                Collectors.toMap(
                        User::city
                        , Function.identity()
                        , BinaryOperator.maxBy(
                                Comparator.nullsLast(
                                        Comparator.comparingLong(
                                                User::id))))
        );
    }
}
