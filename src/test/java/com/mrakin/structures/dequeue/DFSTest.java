package com.mrakin.structures.dequeue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static com.mrakin.structures.dequeue.TreeUtils.createTree;

class DFSTest {
    private final DFSRecursion dfsRecursion = new DFSRecursion();
    private final DFSStack dfsStack = new DFSStack();

    @ParameterizedTest(name = "tree={0}, expected={1}")
    @CsvSource({
            "'1,2,3,4,5', '1,2,4,5,3'",
            "'1,2,3', '1,2,3'",
            "'1,null,2,null,3', '1,2,3'",
            "'1,2,3,4,5,6,7', '1,2,4,5,3,6,7'"
    })
    void testDFS(String treeStr, String expectedStr) {
        TreeNode root = createTree(parseArray(treeStr));
        List<Integer> expected = parseList(expectedStr);

        List<Integer> resRec = new ArrayList<>();
        dfsRecursion.dfs(root, resRec);
        assertEquals(expected, resRec, "Recursion failed");

        List<Integer> resStack = new ArrayList<>();
        dfsStack.dfs(root, resStack);
        assertEquals(expected, resStack, "Stack failed");
    }

    @Test
    void testDeepTree() {
        int depth = 6666;
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
        assertEquals(0, resStack.getFirst());
        assertEquals(depth, resStack.get(depth));
    }

    private Integer[] parseArray(String s) {
        if (s == null || s.trim().isEmpty()) {
            return new Integer[0];
        }
        return Arrays.stream(s.split(","))
                .map(String::trim)
                .map(val -> val.equals("null") ? null : Integer.parseInt(val))
                .toArray(Integer[]::new);
    }

    private List<Integer> parseList(String s) {
        if (s == null || s.trim().isEmpty()) {
            return List.of();
        }
        return Arrays.stream(s.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
