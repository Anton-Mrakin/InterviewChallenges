package com.mrakin.graphs.union.find;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class UnionFindTest {

    @ParameterizedTest(name = "n={0}, unions={1}, checks={2}")
    @CsvSource({
            "5, '0-1, 1-2', '0-2:true, 0-3:false, 3-4:false, 0-1:true, 1-2:true'",
            "10, '0-1, 2-3, 4-5, 6-7, 8-9, 1-3, 5-7, 3-7', '0-7:true, 0-9:false, 1-6:true, 2-5:true'",
            "4, '0-1, 2-3, 0-2', '1-3:true, 0-3:true, 1-2:true'",
            "3, '', '0-1:false, 0-0:true, 1-2:false'",
            "2, '0-1, 1-0', '0-1:true'"
    })
    void testUnionFind(int n, String unionsStr, String checksStr) {
        UnionFind uf = new UnionFind(n);
        
        if (unionsStr != null && !unionsStr.trim().isEmpty()) {
            for (String union : unionsStr.split(",")) {
                String[] nodes = union.trim().split("-");
                if (nodes.length == 2) {
                    uf.union(Integer.parseInt(nodes[0]), Integer.parseInt(nodes[1]));
                }
            }
        }

        if (checksStr != null && !checksStr.trim().isEmpty()) {
            for (String check : checksStr.split(",")) {
                String[] parts = check.trim().split(":");
                String[] nodes = parts[0].split("-");
                boolean expected = Boolean.parseBoolean(parts[1]);
                int a = Integer.parseInt(nodes[0]);
                int b = Integer.parseInt(nodes[1]);
                assertEquals(expected, uf.connected(a, b), "Check " + check + " failed");
            }
        }
    }

    @Test
    void testCount() {
        UnionFind uf = new UnionFind(5);
        assertEquals(5, uf.count());
        uf.union(0, 1);
        assertEquals(4, uf.count());
        uf.union(1, 2);
        assertEquals(3, uf.count());
        uf.union(0, 2); // Already connected
        assertEquals(3, uf.count());
        uf.union(3, 4);
        assertEquals(2, uf.count());
        uf.union(0, 4);
        assertEquals(1, uf.count());
    }

    @Test
    void testInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> new UnionFind(0));
        assertThrows(IllegalArgumentException.class, () -> new UnionFind(-1));
        
        UnionFind uf = new UnionFind(3);
        assertThrows(IndexOutOfBoundsException.class, () -> uf.find(3));
        assertThrows(IndexOutOfBoundsException.class, () -> uf.find(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> uf.union(0, 3));
        assertThrows(IndexOutOfBoundsException.class, () -> uf.connected(0, -1));
    }
}
