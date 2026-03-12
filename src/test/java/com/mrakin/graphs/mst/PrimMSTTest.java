package com.mrakin.graphs.mst;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrimMSTTest {

    @Test
    void testPrimMST() {
        int n = 4;
        List<List<PrimMST.Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        
        PrimMST.addUndirectedEdge(graph, 0, 1, 1);
        PrimMST.addUndirectedEdge(graph, 0, 2, 4);
        PrimMST.addUndirectedEdge(graph, 1, 2, 2);
        PrimMST.addUndirectedEdge(graph, 1, 3, 5);
        PrimMST.addUndirectedEdge(graph, 2, 3, 3);
        
        List<PrimMST.State> mst = PrimMST.prim(n, graph);
        
        int totalWeight = mst.stream().mapToInt(PrimMST.State::weight).sum();
        
        // MST should contain: 0-1 (1), 1-2 (2), 2-3 (3)
        // Total weight = 6
        assertEquals(3, mst.size(), "MST should have n-1 edges");
        assertEquals(6, totalWeight, "Total weight of MST should be 6");
    }

    @Test
    void testDisconnectedGraph() {
        int n = 3;
        List<List<PrimMST.Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        PrimMST.addUndirectedEdge(graph, 0, 1, 5);
        // Node 2 is disconnected
        
        List<PrimMST.State> mst = PrimMST.prim(n, graph);
        assertEquals(1, mst.size(), "Should only include connected components starting from node 0");
        assertEquals(5, mst.get(0).weight());
    }
}
