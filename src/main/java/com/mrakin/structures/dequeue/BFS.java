package com.mrakin.structures.dequeue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class BFS {
    List<Integer> bfs(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode cur = q.poll();
            result.add(cur.val());
            if (cur.left() != null) {
                q.offer(cur.left());
            }
            if (cur.right() != null) {
                q.offer(cur.right());
            }
        }
        return result;
    }
}
