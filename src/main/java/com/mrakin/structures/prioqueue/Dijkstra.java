package com.mrakin.structures.prioqueue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {
    record NodeDistance(int node, int dist) {
    }

    int[] dijkstra(List<List<int[]>> graph, int start) {
        int n = graph.size();
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        PriorityQueue<NodeDistance> pq =
                new PriorityQueue<>(Comparator.comparingInt(NodeDistance::dist));
        pq.offer(new NodeDistance(start, 0));
        while (!pq.isEmpty()) {
            NodeDistance cur = pq.poll();
            if (cur.dist() > dist[cur.node()]) {
                continue; // stale entry
            }
            for (int[] edge : graph.get(cur.node())) {
                int next = edge[0];
                int weight = edge[1];
                int newDist = cur.dist() + weight;
                if (newDist < dist[next]) {
                    dist[next] = newDist;
                    pq.offer(new NodeDistance(next, newDist));
                }
            }
        }
        return dist;
    }
}
