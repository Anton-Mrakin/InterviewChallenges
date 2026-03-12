package com.mrakin.graphs.union.find;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KruskalMST {
    public record Edge(int u, int v, int weight) {
    }

    public static List<Edge> kruskal(int n, List<Edge> edges) {
        if (edges == null || n < 0) {
            return new ArrayList<>();
        }
        if (n == 0) {
            return new ArrayList<>();
        }

        List<Edge> sortedEdges = new ArrayList<>(edges);
        sortedEdges.sort(Comparator.comparingInt(Edge::weight));

        UnionFind uf = new UnionFind(n);
        List<Edge> mst = new ArrayList<>();

        for (Edge edge : sortedEdges) {
            if (edge.u() >= n || edge.v() >= n || edge.u() < 0 || edge.v() < 0) {
                continue;
            }
            if (uf.union(edge.u(), edge.v())) {
                mst.add(edge);
                if (mst.size() == n - 1) {
                    break;
                }
            }
        }
        return mst;
    }
}