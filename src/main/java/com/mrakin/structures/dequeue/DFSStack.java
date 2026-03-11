package com.mrakin.structures.dequeue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class DFSStack {
    public void dfs(TreeNode node, List<Integer> result) {
        if (node == null) return;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(node);
        while(!stack.isEmpty()) {
            TreeNode current = stack.pop();
            result.add(current.val());
            if (current.right() != null) {
                stack.push(current.right());
            }
            if (current.left() != null) {
                stack.push(current.left());
            }
        }
    }
}
