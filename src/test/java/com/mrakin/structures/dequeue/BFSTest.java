package com.mrakin.structures.dequeue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.mrakin.structures.dequeue.TreeUtils.createTree;

class BFSTest {

    private final BFS bfs = new BFS();

    @ParameterizedTest(name = "tree={0}, expected={1}")
    @CsvSource({
            " , ''",
            "'1', '1'",
            "'1,2,3,4,5,6,7', '1,2,3,4,5,6,7'",
            "'1,null,2,null,3', '1,2,3'",
            "'1,2,3,null,4,5', '1,2,3,4,5'"
    })
    void testBFS(String treeStr, String expectedStr) {
        TreeNode root = createTree(parseArray(treeStr));
        assertEquals(parseList(expectedStr), bfs.bfs(root));
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
