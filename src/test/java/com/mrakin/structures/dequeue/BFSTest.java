package com.mrakin.structures.dequeue;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.mrakin.structures.dequeue.TreeUtils.createTree;

class BFSTest {

    private final BFS bfs = new BFS();

    @Test
    void testEmptyTree() {
        assertEquals(List.of(), bfs.bfs(null));
    }

    @Test
    void testSingleNode() {
        TreeNode root = createTree(1);
        assertEquals(List.of(1), bfs.bfs(root));
    }

    @Test
    void testNormalTree() {
        //      1
        //    /   \
        //   2     3
        //  / \   / \
        // 4   5 6   7
        TreeNode root = createTree(1, 2, 3, 4, 5, 6, 7);
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7), bfs.bfs(root));
    }

    @Test
    void testUnbalancedTree() {
        //   1
        //    \
        //     2
        //      \
        //       3
        TreeNode root = createTree(1, null, 2, null, 3);
        assertEquals(List.of(1, 2, 3), bfs.bfs(root));
    }

    @Test
    void testPartialNodes() {
        //      1
        //    /   \
        //   2     3
        //    \   /
        //     4 5
        TreeNode root = createTree(1, 2, 3, null, 4, 5);
        assertEquals(List.of(1, 2, 3, 4, 5), bfs.bfs(root));
    }
}
