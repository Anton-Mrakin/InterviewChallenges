package com.mrakin.graphs.unweighted;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class BFS {
    public static final int UNREACHABLE = -1;

    public int[] findShortestPaths(List<List<Integer>> graph, int start) {
        int nodeCount = graph.size();
        int[] distances = new int[nodeCount];
        Arrays.fill(distances, UNREACHABLE);
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(start);
        distances[start] = 0;

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            for (int neighbor : graph.get(currentNode)) {
                if (distances[neighbor] == UNREACHABLE) {
                    distances[neighbor] = distances[currentNode] + 1;
                    queue.offer(neighbor);
                }
            }
        }
        return distances;
    }
}
