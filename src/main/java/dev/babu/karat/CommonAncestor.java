package dev.babu.karat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Suppose we have some input data describing a graph of relationships between parents and children over
 * multiple generations. The data is formatted as a list of (parent, child) pairs, where each individual
 * is assigned a unique integer identifier. For example, in the graph below, 3 is a child of 1 and 2, and
 * 5 is a child of 4.
 *
 *  parentChildPairs = [ (1,3), (2,3), (3,6), (5,6), (5,7), (4,5), (4,8), (8,10) ]
 *
 *  Write a function that takes this data as input and returns two collections: one containing all
 *  individuals with zero known parents, and one containing all individuals with exactly one known parent.
 *
 *  result => [ [1, 2, 4], // Individuals with zero parents
 *              [5, 7, 8, 10] ] // Individuals with exactly one parent
 *
 *
 */

public class CommonAncestor {

    public static List<List<Integer>> commonAncestors(int[][] pairs) {
        Map<Integer, Integer> numberOfAncestors = new HashMap<>();
        for (int[] pair : pairs) {
            numberOfAncestors.put(pair[1], numberOfAncestors.getOrDefault(pair[1], 0) + 1);
            numberOfAncestors.put(pair[0], numberOfAncestors.getOrDefault(pair[0], 0));
        }

        List<Integer> zeroAncestors = new ArrayList<>();
        List<Integer> oneAncestors = new ArrayList<>();

        for (int node : numberOfAncestors.keySet()) {
            if (numberOfAncestors.get(node) == 0) {
                zeroAncestors.add(node);
            } else if (numberOfAncestors.get(node) == 1) {
                oneAncestors.add(node);
            }
        }

        List<List<Integer>> result = new ArrayList<>();
        result.add(zeroAncestors);
        result.add(oneAncestors);
        return result;
    }

    public static void main(String[] args) {
        int[][] pairs = new int[][] {{1,3}, {2,3}, {3,6}, {5,6}, {5,7}, {4,5}, {4,8}, {8,10}};
        System.out.println(CommonAncestor.commonAncestors(pairs));
    }

}
