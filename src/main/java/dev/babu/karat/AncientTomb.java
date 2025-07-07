package dev.babu.karat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * While exploring an ancient tomb for treasure, you've come across a room with magical spheres, each a different color.
 * You see a number of color-coded holes. To advance to the next room, you need to carry each sphere to one of the holes
 * of the same color. You can only hold one sphere at a time, and you'd like to carry them as short a distance
 * as possible. For example, let's look at the following room layout:
 * room1 = ..b.r..r.R.B...b
 * Capital letters represent spheres.
 * Lower-case letters represent holes.
 * Periods represent empty spaces without holes or spheres.
 * In this case, the red sphere at position 9 can be taken to either 4 or
 * 7, and the blue sphere at 11 can be taken to 2 or 15.
 * Move R sphere 2 spaces left to r
 * +---+
 * v |
 * . . b . r . . r . R . B . . . b
 * | ^
 * +-------+
 * Move B sphere 4 spaces right to b
 * To minimize the time holding the spheres:
 * Carry the red sphere from 9 to 7, a distance of 2
 * Carry the blue sphere from 11 to 15, a distance of 4
 * Total Distance: 6
 * Write a function that, given a room layout, determines the minimum total distance carrying spheres required to put
 * all the spheres in appropriate holes.
 * Note: There will only be one sphere of each color, but there may be multiple places to put each sphere. You may only
 * hold one sphere at once, and only distance carrying a sphere is counted.
 * Additional Input:
 * room2 = "RBGYygbr"
 * room3 = ".........."
 * room4 = "abcbabbcbaAabcabcabbBabbabcabcbbC"
 * room5 = "rRBGYygbr"
 * Complexity Variable:
 * N = size of the room
 * All Test Cases:
 * distance(room2) => 16
 * distance(room3) => 0 There are no spheres, so no distance is required.
 * distance(room4) => 5
 * distance(room5) => 10
 */
public class AncientTomb {

    static int minTotalDistance(String room) {
        Map<Character, Integer> spheres = new HashMap<>(); // Sphere position (uppercase)
        Map<Character, List<Integer>> holes = new HashMap<>(); // Hole positions (lowercase)

        for (int i = 0; i < room.length(); i++) {
            char c = room.charAt(i);
            if (Character.isUpperCase(c)) {
                spheres.put(c, i);
            } else if (Character.isLowerCase(c)) {
                holes.computeIfAbsent(Character.toUpperCase(c), k -> new ArrayList<>()).add(i);
            }
        }

        int totalDistance = 0;

        for (Map.Entry<Character, Integer> entry : spheres.entrySet()) {
            char color = entry.getKey(); // Sphere color
            int spherePos = entry.getValue();
            List<Integer> holePositions = holes.get(color);

            if (holePositions == null || holePositions.isEmpty()) {
                throw new IllegalArgumentException("No hole found for color: " + color);
            }

            int minDist = Integer.MAX_VALUE;
            for (int holePos : holePositions) {
                minDist = Math.min(minDist, Math.abs(spherePos - holePos));
            }

            totalDistance += minDist;
        }

        return totalDistance;
    }

    public static void main(String[] args) {
        String room1 = "..b.r..r.R.B...b";
        int result = minTotalDistance(room1);
        System.out.println("Minimum total distance: " + result); // Expected: 6

        String room2 = "RBGYygbr";
        result = minTotalDistance(room2);
        System.out.println("Minimum total distance: " + result); // Expected: 16
    }
}
