package dev.babu.karat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Write a function that, for a given individual in our dataset, returns their earliest known ancestor --
 * the one at the farthest distance from the input individual. If there is more than one ancestor tied for
 * "earliest", return any one of them. If the input individual has no parents, the function should return
 * null (or -1).
 *
 * Sample Input & Output:
 * parent_child_pairs = [ (1, 3), (2, 3), (3, 6), (5, 6), (5, 7), (4, 5), (4, 8), (8, 10), (11, 2) ]
 *
 * findEarliestAncestor(parentChildPairs, 8) => 4
 * findEarliestAncestor(parentChildPairs, 7) => 4
 * findEarliestAncestor(parentChildPairs, 6) => 11
 * findEarliestAncestor(parentChildPairs, 1) => null or -1
 */
public class EarliestAncestor {

    /**
     * For a given person, the earliest known ancestor is the one with the longest path away
     * (i.e. deepest in terms of generations above the person).
     *
     * Build a reverse graph: child → list of parents.
     *
     * Do DFS from the person to all ancestors, tracking:
     *
     * Depth of each ancestor.
     *
     * Update the earliest ancestor based on:
     *
     * Longest depth.
     *
     * If tied, any one is acceptable (or you can pick the smallest ID).
     *
     * If no parents found → return -1.
     */
    public static int findEarliestAncestor(int[][] parentChildPairs, int person) {
        // Step 1: Build child → list of parents graph
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] pair : parentChildPairs) {
            int parent = pair[0];
            int child = pair[1];
            graph.computeIfAbsent(child, k -> new ArrayList<>()).add(parent);
        }

        // Step 2: DFS to find the farthest ancestor
        int[] result = new int[]{-1}; // stores the earliest ancestor
        int[] maxDepth = new int[]{-1}; // tracks max depth

        dfs(person, graph, 0, result, maxDepth);

        return result[0];
    }

    // DFS with depth tracking
    private static void dfs(int current, Map<Integer, List<Integer>> graph, int depth, int[] result, int[] maxDepth) {
        if (!graph.containsKey(current)) {
            if (depth > maxDepth[0]) {
                maxDepth[0] = depth;
                result[0] = current;
            }
            return;
        }

        for (int parent : graph.get(current)) {
            dfs(parent, graph, depth + 1, result, maxDepth);
        }
    }

    public static void main(String[] args) {
        int[][] parentChildPairs = {
                {1, 3}, {2, 3}, {3, 6}, {5, 6},
                {5, 7}, {4, 5}, {4, 8}, {8, 10}, {11, 2}
        };

        System.out.println(findEarliestAncestor(parentChildPairs, 8));  // 4
        System.out.println(findEarliestAncestor(parentChildPairs, 7));  // 4
        System.out.println(findEarliestAncestor(parentChildPairs, 6));  // 11
        System.out.println(findEarliestAncestor(parentChildPairs, 1));  // -1
    }
}
