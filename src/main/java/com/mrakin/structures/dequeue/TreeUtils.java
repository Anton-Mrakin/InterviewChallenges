package com.mrakin.structures.dequeue;

import java.util.ArrayDeque;
import java.util.Queue;

public class TreeUtils {
    /**
     * Creates a tree from level-order traversal (LeetCode style).
     */
    public static TreeNode createTree(Integer... vals) {
        if (vals == null || vals.length == 0 || vals[0] == null) return null;

        Queue<TreeNodeBuilder> queue = new ArrayDeque<>();
        TreeNodeBuilder rootBuilder = new TreeNodeBuilder(vals[0]);
        queue.offer(rootBuilder);

        int i = 1;
        while (!queue.isEmpty() && i < vals.length) {
            TreeNodeBuilder current = queue.poll();

            if (i < vals.length && vals[i] != null) {
                current.left = new TreeNodeBuilder(vals[i]);
                queue.offer(current.left);
            }
            i++;

            if (i < vals.length && vals[i] != null) {
                current.right = new TreeNodeBuilder(vals[i]);
                queue.offer(current.right);
            }
            i++;
        }
        return rootBuilder.build();
    }

    private static class TreeNodeBuilder {
        int val;
        TreeNodeBuilder left;
        TreeNodeBuilder right;

        TreeNodeBuilder(int val) {
            this.val = val;
        }

        TreeNode build() {
            return new TreeNode(val,
                    left == null ? null : left.build(),
                    right == null ? null : right.build());
        }
    }
}
