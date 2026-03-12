package com.mrakin.graphs.union.find;

import com.mrakin.graphs.mst.KruskalMST;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KruskalMSTTest {

    @ParameterizedTest(name = "n={0}, edges={1}, expectedWeight={2}")
    @CsvSource({
            "4, '0-1:1, 0-2:4, 1-2:2, 1-3:5, 2-3:3', 6",
            "3, '0-1:10, 1-2:20, 0-2:5', 15",
            "4, '0-1:1, 2-3:2', 3", // Disconnected components
            "1, '', 0",
            "0, '', 0",
            "4, '0-1:1, 0-1:2, 1-2:3', 4" // Duplicate edges
    })
    void testKruskal(int n, String edgesStr, int expectedWeight) {
        List<KruskalMST.Edge> edges = parseEdges(edgesStr);
        List<KruskalMST.Edge> mst = KruskalMST.kruskal(n, edges);
        
        int totalWeight = mst.stream().mapToInt(KruskalMST.Edge::weight).sum();
        assertEquals(expectedWeight, totalWeight);
        assert n <= 0 || (mst.size() <= Math.max(0, n - 1));
    }

    private List<KruskalMST.Edge> parseEdges(String s) {
        List<KruskalMST.Edge> edges = new ArrayList<>();
        if (s == null || s.trim().isEmpty()) {
            return edges;
        }
        String[] parts = s.split(",");
        for (String part : parts) {
            String[] edgeWeight = part.trim().split(":");
            String[] nodes = edgeWeight[0].split("-");
            int u = Integer.parseInt(nodes[0]);
            int v = Integer.parseInt(nodes[1]);
            int weight = Integer.parseInt(edgeWeight[1]);
            edges.add(new KruskalMST.Edge(u, v, weight));
        }
        return edges;
    }
}
