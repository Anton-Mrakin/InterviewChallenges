package com.mrakin.graphs.directed.acyclic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KahnTest {

    private final Kahn kahn = new Kahn();

    @ParameterizedTest(name = "n={0}, graph={1}, expected={2}")
    @CsvSource({
            "3, '0-1, 1-2', '0,1,2'",
            "1, '', '0'",
            "4, '0-1, 0-2, 1-3, 2-3', '0,1,2,3'",
            "2, '', '0,1'",
            "5, '0-2, 1-2, 2-3, 2-4', '0,1,2,3,4'",
            "0, '', ''"
    })
    void testTopoSort(int n, String graphStr, String expectedStr) {
        List<List<Integer>> graph = parseGraph(n, graphStr);
        List<Integer> expected = parseList(expectedStr);
        assertEquals(expected, kahn.topoSort(n, graph));
    }

    @Test
    void testCycle() {
        int n = 3;
        List<List<Integer>> graph = parseGraph(n, "0-1, 1-2, 2-0");
        assertThrows(IllegalStateException.class, () -> kahn.topoSort(n, graph));
    }

    private List<List<Integer>> parseGraph(int n, String s) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        if (s == null || s.trim().isEmpty()) {
            return graph;
        }
        String[] edges = s.split(",");
        for (String edge : edges) {
            String[] nodes = edge.trim().split("-");
            if (nodes.length == 2) {
                int u = Integer.parseInt(nodes[0]);
                int v = Integer.parseInt(nodes[1]);
                graph.get(u).add(v);
            }
        }
        return graph;
    }

    private List<Integer> parseList(String s) {
        if (s == null || s.trim().isEmpty()) {
            return List.of();
        }
        return Arrays.stream(s.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
    }
}
