package com.mrakin.structures.dequeue;

import java.util.List;

public class DFSRecursion {
    void dfs(TreeNode node, List<Integer> result) {
        if (node == null) return;
        result.add(node.val()); // preorder
        dfs(node.left(), result);
        dfs(node.right(), result);
    }
}
