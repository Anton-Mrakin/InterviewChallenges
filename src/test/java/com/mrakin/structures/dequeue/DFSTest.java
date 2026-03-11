package com.mrakin.structures.dequeue;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DFSTest {
    private final DFSRecursion dfsRecursion = new DFSRecursion();
    private final DFSStack dfsStack = new DFSStack();

    @Test
    void testSmallTree() {
        TreeNode root = TreeUtils.createTree(1, 2, 3, 4, 5);
        List<Integer> resRec = new ArrayList<>();
        dfsRecursion.dfs(root, resRec);

        List<Integer> resStack = new ArrayList<>();
        dfsStack.dfs(root, resStack);

        assertEquals(List.of(1, 2, 4, 5, 3), resRec);
        assertEquals(List.of(1, 2, 4, 5, 3), resStack);
    }

    @Test
    void testDeepTree() {
        int depth = 50000;
        TreeNode root = new TreeNode(depth, null, null);
        for (int i = depth - 1; i >= 0; i--) {
            root = new TreeNode(i, root, null);
        }

        final TreeNode finalRoot = root;

        // Verify recursion fails at this depth
        assertThrows(StackOverflowError.class, () -> {
            dfsRecursion.dfs(finalRoot, new ArrayList<>());
        }, "Expected StackOverflowError for recursion at depth " + depth);

        // Verify stack-based approach works
        List<Integer> resStack = new ArrayList<>();
        assertDoesNotThrow(() -> {
            dfsStack.dfs(finalRoot, resStack);
        }, "DFSStack should not throw for depth " + depth);
        assertEquals(depth + 1, resStack.size());
        assertEquals(0, resStack.get(0));
        assertEquals(depth, resStack.get(depth));
    }
}
