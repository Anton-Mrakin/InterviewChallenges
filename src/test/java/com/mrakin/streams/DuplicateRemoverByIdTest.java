package com.mrakin.streams;

import com.mrakin.streams.data.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Collection;
import java.util.List;

public class DuplicateRemoverByIdTest {

    private final DuplicateRemoverById remover = new DuplicateRemoverById();

    @Test
    public void testRemoveDuplicatesById() {
        List<User> users = List.of(
                new User(1L, "Alice", "New York"),
                new User(2L, "Bob", "Los Angeles"),
                new User(3L, "Alice", "San Francisco"), // Дубликат имени "Alice"
                new User(4L, "Charlie", "Chicago"),
                new User(5L, "Bob", "San Francisco") // Дубликат имени "Bob"
        );

        Collection<User> expected = List.of(
                new User(1L, "Alice", "New York"),
                new User(2L, "Bob", "Los Angeles"),
                new User(4L, "Charlie", "Chicago")
        );
        Collection<User> result = remover.removeDuplicatesById(users);

        Assertions.assertEquals(expected.toString(), result.toString());
    }

    @Test
    public void testRemoveDuplicatesByIdNoDuplicates() {
        List<User> users = List.of(
                new User(1L, "Alice", "New York"),
                new User(2L, "Bob", "Los Angeles"),
                new User(3L, "Charlie", "San Francisco")
        );

        Collection<User> expected = List.of(
                new User(1L, "Alice", "New York"),
                new User(2L, "Bob", "Los Angeles"),
                new User(3L, "Charlie", "San Francisco")
        );

        Collection<User> result = remover.removeDuplicatesById(users);

        Assertions.assertEquals(expected.toString(), result.toString());
    }

    @Test
    public void testRemoveDuplicatesByIdEmptyList() {
        List<User> users = List.of();

        Collection<User> expected = List.of();

        Collection<User> result = remover.removeDuplicatesById(users);

        Assertions.assertEquals(expected.toString(), result.toString());
    }

    @Test
    public void testRemoveDuplicatesByIdNullNames() {
        List<User> users = List.of(
                new User(1L, null, "New York"),
                new User(2L, "Alice", "Los Angeles"),
                new User(3L, null, "San Francisco"), // Дубликат по null-значению имени
                new User(4L, "Charlie", "Chicago")
        );

        Collection<User> expected = List.of(
                new User(1L, null, "New York"),
                new User(2L, "Alice", "Los Angeles"),
                new User(4L, "Charlie", "Chicago")
        );

        Collection<User> result = remover.removeDuplicatesById(users);

        Assertions.assertEquals(expected.toString(), result.toString());
    }
}