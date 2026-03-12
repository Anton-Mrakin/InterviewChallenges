package com.mrakin.graphs.mst;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PrimMST {
    public record Edge(int to, int weight) {
    }

    public record State(int from, int to, int weight) {
    }

    public static List<State> prim(int n, List<List<Edge>> graph) {
        boolean[] inMst = new boolean[n];
        List<State> mst = new ArrayList<>();
        PriorityQueue<State> pq =
                new PriorityQueue<>(Comparator.comparingInt(State::weight));
        // start from node 0
        inMst[0] = true;
        for (Edge e : graph.getFirst()) {
            pq.offer(new State(0, e.to(), e.weight()));
        }
        while (!pq.isEmpty() && mst.size() < n - 1) {
            State cur = pq.poll();
            if (inMst[cur.to()]) {
                continue; // stale edge, or leads inside current tree
            }
            // take this edge into MST
            inMst[cur.to()] = true;
            mst.add(cur);
            // expand frontier
            for (Edge e : graph.get(cur.to())) {
                if (!inMst[e.to()]) {
                    pq.offer(new State(cur.to(), e.to(), e.weight()));
                }
            }
        }
        return mst;
    }

    public static void addUndirectedEdge(List<List<Edge>> graph, int u, int v, int w) {
        graph.get(u).add(new Edge(v, w));
        graph.get(v).add(new Edge(u, w));
    }
}
