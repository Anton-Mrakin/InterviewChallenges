package com.mrakin.streams;

import com.mrakin.streams.data.Department;
import com.mrakin.streams.data.Skill;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FlatMapping {
    public Map<String, Set<Skill>> flatMapping(List<Department> departments) {
        return departments.stream()
                .collect(Collectors.groupingBy(
                        Department::name,
                        Collectors.flatMapping(
                                dept -> dept.employees()
                                        .stream()
                                        .flatMap(emp -> emp.skills().stream()),
                                Collectors.toSet()
                        )
                ));
    }
}
