package com.mrakin.structures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SortedListMerger {
    record Entry(int value, int listIndex, int elementIndex) {
    }

    List<Integer> mergeSortedLists(List<List<Integer>> lists) {
        PriorityQueue<Entry> pq = new PriorityQueue<>(Comparator.comparingInt(Entry::value));
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i).isEmpty()) {
                continue;
            }
            pq.offer(
                    new Entry(
                            lists.get(i).getFirst()
                            , i
                            , 0));
        }
        while (!pq.isEmpty()) {
            Entry cur = pq.poll();
            result.add(cur.value());
            int nextIndex = cur.elementIndex() + 1;
            List<Integer> list = lists.get(cur.listIndex());
            if (nextIndex >= list.size()) {
                continue;
            }
            pq.offer(
                    new Entry(
                            list.get(nextIndex)
                            , cur.listIndex(), nextIndex));
        }
        return result;
    }
}
