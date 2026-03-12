package com.mrakin.graphs.directed.acyclic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DFS {
    public List<Integer> topoSort(int n, List<List<Integer>> graph) {
        if (graph == null || n < 0) {
            return new ArrayList<>();
        }

        boolean[] visited = new boolean[n];
        boolean[] visiting = new boolean[n];
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(i, graph, visited, visiting, result);
            }
        }

        Collections.reverse(result);
        return result;
    }

    private void dfs(int u, List<List<Integer>> graph, boolean[] visited,
                     boolean[] visiting, List<Integer> result) {
        if (visiting[u]) {
            throw new IllegalStateException("Graph has a cycle");
        }

        if (visited[u]) {
            return;
        }

        visiting[u] = true;

        if (u < graph.size() && graph.get(u) != null) {
            for (int v : graph.get(u)) {
                dfs(v, graph, visited, visiting, result);
            }
        }

        visiting[u] = false;
        visited[u] = true;
        result.add(u);
    }
}
