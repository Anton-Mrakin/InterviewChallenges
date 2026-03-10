package com.mrakin.streams;

import com.mrakin.streams.data.User;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DuplicateRemoverById {
    public Collection<User> removeDuplicatesById(List<User> users) {
        return
                users.stream()
                        .collect(
                        Collectors.toMap(
                                User::name,
                                Function.identity(),
                                (first, second) -> first,
                                LinkedHashMap::new
                        ))
                        .values();
    }

}
