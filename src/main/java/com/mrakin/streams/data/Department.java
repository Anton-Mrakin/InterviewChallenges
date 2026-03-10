package com.mrakin.streams.data;

import java.util.List;

public record Department(String name, List<User> employees) {
}
