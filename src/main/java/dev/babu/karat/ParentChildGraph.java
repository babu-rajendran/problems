package dev.babu.karat;

import java.util.*;

public class ParentChildGraph {

    /**
     * Suppose we have some input data describing a graph of relationships between parents
     * and children over multiple generations. The data is formatted as a list of (parent, child) pairs,
     * where each individual is assigned a unique integer identifier.
     * For example, in this diagram, 3 is a child of 1 and 2, and 5 is a child of 4:
     *
     * parentChildPairs = [  (1, 3), (2, 3), (3, 6), (5, 6),
     *                    (5, 7), (4, 5), (4, 8), (8, 10)  ]
     * Write a function that takes this data as input and returns two collections:
     * one containing all individuals with zero known parents, and
     * one containing all individuals with exactly one known parent.
     *
     * Sample output:
     * findNodesWithZeroAndOneParents(parentChildPairs) =>
     *                                   [ [1, 2, 4],    // Individuals with zero parents
     *                                   [5, 7, 8, 10] // Individuals with exactly one parent ]
     *
     */
    List<List<Integer>> findNodesWithZeroAndOneParents(int[][] parentChildPairs) {
        Map<Integer, Integer> childToParentCount = new HashMap<>();
        Set<Integer> allIndividuals = new HashSet<>();

        for (int[] pair : parentChildPairs) {
            int parent = pair[0];
            int child = pair[1];

            // Add both parent and child to the set of all individuals
            allIndividuals.add(parent);
            allIndividuals.add(child);

            // Increment parent count for the child
            childToParentCount.put(child, childToParentCount.getOrDefault(child, 0) + 1);
        }

        List<Integer> zeroParents = new ArrayList<>();
        List<Integer> oneParent = new ArrayList<>();

        for (int person : allIndividuals) {
            int parentCount = childToParentCount.getOrDefault(person, 0);
            if (parentCount == 0) {
                zeroParents.add(person);
            } else if (parentCount == 1) {
                oneParent.add(person);
            }
        }

        return Arrays.asList(zeroParents, oneParent);
    }

    /**
     * Write a function that takes the graph, as well as two of the individuals in our dataset,
     * as its inputs and returns true if and only if they share at least one ancestor.
     *
     * Sample Output:
     * hasCommonAncestor(parentChildPairs, 3, 8) => false
     * hasCommonAncestor(parentChildPairs, 5, 8) => true
     * hasCommonAncestor(parentChildPairs, 6, 8) => true
     * hasCommonAncestor(parentChildPairs, 1, 3) => false
     *
     * Plan:
     * Build a child -> list of parents graph.
     *
     * Write a helper method to get all ancestors of an individual using DFS.
     *
     * Check if the intersection of the two ancestor sets is non-empty → return true.
     */
    boolean hasCommonAncestor(int[][] parentChildPairs, int person1, int person2) {
        // Step 1: Build child -> list of parents map
        Map<Integer, List<Integer>> childToParents = new HashMap<>();
        for (int[] pair : parentChildPairs) {
            int parent = pair[0];
            int child = pair[1];
            childToParents.computeIfAbsent(child, k -> new ArrayList<>()).add(parent);
        }

        // Step 2: Get all ancestors for both individuals
        Set<Integer> ancestors1 = getAncestors(person1, childToParents);
        Set<Integer> ancestors2 = getAncestors(person2, childToParents);

        // Step 3: Check for any intersection
        for (int ancestor : ancestors1) {
            if (ancestors2.contains(ancestor)) {
                return true;
            }
        }

        return false;
    }

    // Helper to recursively collect all ancestors using DFS
    static Set<Integer> getAncestors(int person, Map<Integer, List<Integer>> graph) {
        Set<Integer> ancestors = new HashSet<>();
        Deque<Integer> stack = new ArrayDeque<>();

        if (graph.containsKey(person)) {
            stack.addAll(graph.get(person));
        }

        while (!stack.isEmpty()) {
            int current = stack.pop();
            if (ancestors.add(current)) {
                if (graph.containsKey(current)) {
                    stack.addAll(graph.get(current));
                }
            }
        }

        return ancestors;
    }

    public static void main(String[] args) {
        ParentChildGraph parentChildGraph = new ParentChildGraph();
        int[][] parentChildPairs = {
                {1, 3}, {2, 3}, {3, 6}, {5, 6},
                {5, 7}, {4, 5}, {4, 8}, {8, 10}
        };

        List<List<Integer>> result = parentChildGraph.findNodesWithZeroAndOneParents(parentChildPairs);

        System.out.println("Zero parents: " + result.get(0));
        System.out.println("One parent: " + result.get(1));

        System.out.println(parentChildGraph.hasCommonAncestor(parentChildPairs, 3, 8)); // false
        System.out.println(parentChildGraph.hasCommonAncestor(parentChildPairs, 5, 8)); // true
        System.out.println(parentChildGraph.hasCommonAncestor(parentChildPairs, 6, 8)); // true
        System.out.println(parentChildGraph.hasCommonAncestor(parentChildPairs, 1, 3)); // false
    }
}
