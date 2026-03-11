package com.mrakin.graphs.unweighted;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class BFSTest {
    @ParameterizedTest
    @CsvSource({
            // graphStr, startNode, expectedResultStr
            "'1,3 | 0,2,4 | 1 | 0,4 | 1,3', 0, '0,1,2,1,2'", // Базовый граф
            "'1 | 0 | 3 | 2',               0, '0,1,-1,-1'", // Несвязный граф
            "'',                            0, '0'",          // Один узел
            "'1,2 | 0,2 | 0,1',             0, '0,1,1'"       // Цикл
    })
    void testShortestPaths(String graphStr, int startNode, String expectedResultStr) {
        List<List<Integer>> graph = parseGraph(graphStr);
        int[] expected = parseArray(expectedResultStr);
        BFS bfs = new BFS();
        int[] result = bfs.findShortestPaths(graph, startNode);
        Assertions.assertArrayEquals(expected, result);
    }

    private List<List<Integer>> parseGraph(String graphStr) {
        if (graphStr == null || graphStr.trim().isEmpty()) {
            return Collections.singletonList(Collections.emptyList());
        }
        String[] nodes = graphStr.split("\\|");
        List<List<Integer>> graph = new ArrayList<>();
        for (String nodeStr : nodes) {
            nodeStr = nodeStr.trim();
            if (nodeStr.isEmpty()) {
                graph.add(Collections.emptyList());
            } else {
                graph.add(Arrays.stream(nodeStr.split(","))
                        .map(String::trim)
                        .map(Integer::parseInt)
                        .toList());
            }
        }
        return graph;
    }

    private int[] parseArray(String arrayStr) {
        if (arrayStr == null || arrayStr.trim().isEmpty()) {
            return new int[0];
        }
        return Arrays.stream(arrayStr.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    @Test
    void testEmptyGraph() {
        List<List<Integer>> graph = new ArrayList<>();
        BFS bfs = new BFS();
        Assertions.assertThrows(Exception.class, () -> bfs.findShortestPaths(graph, 0));
    }
}
