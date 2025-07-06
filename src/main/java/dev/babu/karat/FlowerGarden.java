package dev.babu.karat;

import java.util.HashMap;
import java.util.Map;

public class FlowerGarden {

    /**
     * You are given a list of strings representing a row of flowers in a garden. Each element in the array is a flower
     * type, represented by its name (a string). Some flower types may appear multiple times, and others may appear
     * only once.
     *
     * Your task is to find the maximum distance (in terms of indices) between two flowers of the same type
     * in the garden.
     *
     * Return the maximum such distance.
     * If there are no flower types that repeat, return -1.
     *
     * Constraints:
     * The input array garden is not null.
     *
     * Flower types are non-empty strings.
     *
     * The array may contain 0 or more elements.
     *
     * Example:
     * Input: ["rose", "lily", "tulip", "rose", "daisy", "lily"]
     * Output: 3
     * Explanation:
     * - "rose" appears at index 0 and 3 → distance = 3
     * - "lily" appears at index 1 and 5 → distance = 4
     * → Maximum = 4
     */
    static int maxDistance(String[] garden) {
        if (garden == null || garden.length <= 1) {
            return -1;
        }

        Map<String, Integer> firstSeen = new HashMap<>();
        int maxDistance = -1;

        for (int i = 0; i < garden.length; i++) {
            String flower = garden[i];

            if (firstSeen.containsKey(flower)) {
                int distance = i - firstSeen.get(flower);
                maxDistance = Math.max(maxDistance, distance);
            } else {
                firstSeen.put(flower, i);
            }
        }

        return maxDistance;
    }

    public static void main(String[] args) {
        // Test case 1
        String[] garden1 = {"rose", "lily", "tulip", "rose", "daisy", "lily"};
        System.out.println("Test 1 Output: " + maxDistance(garden1)); // Expected: 4

        // Test case 2
        String[] garden2 = {"orchid", "tulip", "rose"};
        System.out.println("Test 2 Output: " + maxDistance(garden2)); // Expected: -1
    }



}
