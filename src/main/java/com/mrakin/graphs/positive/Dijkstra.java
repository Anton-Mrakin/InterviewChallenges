package com.mrakin.graphs.positive;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {
    private record NodeDistance(int node, int dist) {
    }

    public int[] dijkstra(List<List<int[]>> graph, int start) {
        if (graph == null || start < 0 || start >= graph.size()) {
            return new int[0];
        }
        int n = graph.size();
        int[] dists = new int[n];
        Arrays.fill(dists, Integer.MAX_VALUE);
        dists[start] = 0;
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(Comparator.comparingInt(NodeDistance::dist));
        pq.offer(new NodeDistance(start, 0));
        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();
            if (current.dist() > dists[current.node()]) {
                continue;
            }
            for (int[] edge : graph.get(current.node())) {
                int v = edge[0];
                int weight = edge[1];
                int newDist = current.dist() + weight;
                if (newDist < dists[v]) {
                    dists[v] = newDist;
                    pq.offer(new NodeDistance(v, newDist));
                }
            }
        }
        return dists;
    }
}
