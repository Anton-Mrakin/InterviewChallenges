package com.mrakin.graphs.directed.acyclic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Kahn {
    public List<Integer> topoSort(int n, List<List<Integer>> graph) {
        if (graph == null || n < 0) {
            return new ArrayList<>();
        }

        int[] indegree = new int[n];
        for (int u = 0; u < n; u++) {
            for (int v : graph.get(u)) {
                indegree[v]++;
            }
        }

        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                q.offer(i);
            }
        }

        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (int v : graph.get(u)) {
                indegree[v]--;
                if (indegree[v] == 0) {
                    q.offer(v);
                }
            }
        }

        if (order.size() != n) {
            throw new IllegalStateException("Graph has a cycle");
        }

        return order;
    }
}
