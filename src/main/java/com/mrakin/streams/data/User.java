package com.mrakin.streams.data;

import java.util.Set;

public record User(Long id, String name, String city, Set<Skill> skills, Department department) {
    public User(Long id, String name, String city) {
        this(id, name, city, Set.of(), null);
    }
}
