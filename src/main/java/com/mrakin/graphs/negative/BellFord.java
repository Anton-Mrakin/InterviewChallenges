package com.mrakin.graphs.negative;

import java.util.Arrays;
import java.util.List;

public class BellFord {
    public record Edge(int from, int to, int weight) {
    }

    public int[] bellmanFord(int n, List<Edge> edges, int start) {
        if (edges == null || start < 0 || start >= n) {
            return new int[0];
        }
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        for (int i = 0; i < n - 1; i++) {
            boolean changed = false;
            for (Edge e : edges) {
                int newDistance = dist[e.from()] + e.weight();
                if (dist[e.from()] != Integer.MAX_VALUE &&
                        newDistance < dist[e.to()]) {
                    dist[e.to()] = newDistance;
                    changed = true;
                }
            }
            if (!changed) break;
        }
        return dist;
    }

    public boolean hasNegativeCycle(int[] dist, List<Edge> edges) {
        if (dist == null || dist.length == 0 || edges == null) {
            return false;
        }
        for (Edge e : edges) {
            if (dist[e.from()] != Integer.MAX_VALUE &&
                    dist[e.from()] + e.weight() < dist[e.to()]) {
                return true;
            }
        }
        return false;
    }
}
