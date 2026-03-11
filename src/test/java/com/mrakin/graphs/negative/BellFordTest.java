package com.mrakin.graphs.negative;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class BellFordTest {

    private final BellFord bf = new BellFord();

    @ParameterizedTest(name = "n={0}, edges={1}, start={2}, expectedDist={3}, hasCycle={4}")
    @CsvSource({
            // Обычный граф без отрицательных ребер
            "3, '0-1:10, 1-2:5, 0-2:20', 0, '0,10,15', false",
            // Граф с отрицательными ребрами, но без отрицательных циклов
            "4, '0-1:5, 1-2:2, 2-3:-10, 0-2:10', 0, '0,5,7,-3', false",
            // Граф с отрицательным циклом
            "3, '0-1:1, 1-2:1, 2-0:-5', 0, '-3,-2,-1', true",
            // Несвязный граф
            "3, '0-1:5', 0, '0,5,INF', false",
            // Одна вершина
            "1, '', 0, '0', false",
            // Отрицательный цикл, не достижимый из старта
            "4, '0-1:5, 2-3:1, 3-2:-5', 0, '0,5,INF,INF', false"
    })
    void testBellmanFord(int n, String edgesStr, int start, String expectedDistStr, boolean expectedHasCycle) {
        List<BellFord.Edge> edges = parseEdges(edgesStr);
        int[] dists = bf.bellmanFord(n, edges, start);
        
        boolean actualHasCycle = bf.hasNegativeCycle(dists, edges);
        assertEquals(expectedHasCycle, actualHasCycle, "Negative cycle detection failed");

        if (!expectedHasCycle) {
            assertArrayEquals(parseExpectedDist(expectedDistStr), dists);
        }
    }

    private List<BellFord.Edge> parseEdges(String s) {
        List<BellFord.Edge> edges = new ArrayList<>();
        if (s == null || s.trim().isEmpty() || s.equals("''")) {
            return edges;
        }
        String[] parts = s.split(",");
        for (String part : parts) {
            // Формат "from-to:weight"
            String[] edgeWeight = part.trim().split(":");
            String[] fromTo = edgeWeight[0].split("-");
            int from = Integer.parseInt(fromTo[0]);
            int to = Integer.parseInt(fromTo[1]);
            int weight = Integer.parseInt(edgeWeight[1]);
            edges.add(new BellFord.Edge(from, to, weight));
        }
        return edges;
    }

    private int[] parseExpectedDist(String s) {
        if (s == null || s.trim().isEmpty()) {
            return new int[0];
        }
        String[] parts = s.split(",");
        int[] res = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            String p = parts[i].trim();
            if (p.equals("INF")) {
                res[i] = Integer.MAX_VALUE;
            } else {
                res[i] = Integer.parseInt(p);
            }
        }
        return res;
    }
}
