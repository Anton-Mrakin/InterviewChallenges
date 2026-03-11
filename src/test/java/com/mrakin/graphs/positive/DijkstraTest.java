package com.mrakin.graphs.positive;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class DijkstraTest {

    private final Dijkstra solver = new Dijkstra();

    @ParameterizedTest(name = "n={0}, edges={1}, start={2}, expected={3}")
    @CsvSource({
            "5, '0-1:10, 0-2:5, 1-2:2, 1-3:1, 2-1:3, 2-3:9, 2-4:2, 3-4:4, 4-0:7, 4-3:6', 0, '0,8,5,9,7'",
            "3, '0-1:1, 1-2:2, 0-2:4', 0, '0,1,3'",
            "3, '0-1:1', 0, '0,1,2147483647'",
            "1, '', 0, '0'",
            "3, '0-1:1, 1-0:1', 2, '2147483647,2147483647,0'",
            "4, '0-1:1, 0-2:1, 1-3:1, 2-3:1', 0, '0,1,1,2'"
    })
    void testDijkstra(int n, String edgesStr, int start, String expectedStr) {
        List<List<int[]>> graph = parseGraph(edgesStr, n);
        int[] expected = parseArray(expectedStr);
        assertArrayEquals(expected, solver.dijkstra(graph, start));
    }

    private List<List<int[]>> parseGraph(String s, int n) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        if (s == null || s.trim().isEmpty() || s.equals("''")) {
            return graph;
        }
        String[] edges = s.split(",");
        for (String edge : edges) {
            edge = edge.trim();
            if (edge.isEmpty()) continue;
            // Формат: from-to:weight
            String[] parts = edge.split("[-:]");
            if (parts.length < 3) continue;
            int from = Integer.parseInt(parts[0]);
            int to = Integer.parseInt(parts[1]);
            int weight = Integer.parseInt(parts[2]);
            if (from < n) {
                graph.get(from).add(new int[]{to, weight});
            }
        }
        return graph;
    }

    private int[] parseArray(String s) {
        if (s == null || s.trim().isEmpty() || s.equals("''")) {
            return new int[0];
        }
        return Arrays.stream(s.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
