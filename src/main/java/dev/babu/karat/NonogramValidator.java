package dev.babu.karat;

import java.util.*;

/**
 * A nonogram is a logic puzzle in which you are given a blank grid and have to fill it with black (0) and white (1)
 * cells according to certain rules.
 *
 * Each row and each column comes with a clue — a list of integers describing the lengths of contiguous blocks of black
 * cells (0s) that must appear in that row/column in order. There must be at least one white cell (1) between blocks.
 *
 * Given:
 *
 * A char[][] matrix where each cell is either '0' (black) or '1' (white)
 *
 * An int[][] rowClues — clues for each row
 *
 * An int[][] colClues — clues for each column
 *
 * Write a method:
 *
 * public boolean isValid(char[][] matrix, int[][] rowClues, int[][] colClues)
 *
 * that returns true if the matrix matches both the row and column clues, otherwise false.
 */

public class NonogramValidator {

    public static boolean isValid(char[][] matrix, int[][] rowClues, int[][] colClues) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        // Validate each row
        for (int i = 0; i < rows; i++) {
            List<Integer> actual = extractClues(matrix[i]);
            if (!matchesClue(actual, rowClues[i])) {
                return false;
            }
        }

        // Validate each column
        for (int j = 0; j < cols; j++) {
            char[] col = new char[rows];
            for (int i = 0; i < rows; i++) {
                col[i] = matrix[i][j];
            }
            List<Integer> actual = extractClues(col);
            if (!matchesClue(actual, colClues[j])) {
                return false;
            }
        }

        return true;
    }

    // Extracts black cell (0) blocks from a row or column
    private static List<Integer> extractClues(char[] line) {
        List<Integer> result = new ArrayList<>();
        int count = 0;

        for (char c : line) {
            if (c == '0') {
                count++;
            } else {
                if (count > 0) {
                    result.add(count);
                    count = 0;
                }
            }
        }

        if (count > 0) {
            result.add(count);
        }

        return result;
    }

    // Checks if actual clues match expected
    private static boolean matchesClue(List<Integer> actual, int[] expected) {
        if (actual.size() != expected.length) return false;
        for (int i = 0; i < expected.length; i++) {
            if (actual.get(i) != expected[i]) return false;
        }
        return true;
    }

    // --- Example usage ---
    public static void main(String[] args) {
        char[][] matrix = {
                {'1','1','1','1'},
                {'0','1','1','1'},
                {'0','1','0','0'},
                {'1','1','0','1'},
                {'0','0','1','1'}
        };

        int[][] rowClues = {
                {},
                {1},
                {1, 2},
                {1},
                {2}
        };

        int[][] colClues = {
                {2, 1},
                {1},
                {2},
                {1}
        };

        boolean result = isValid(matrix, rowClues, colClues);
        System.out.println("Matrix is valid: " + result);
    }
}
