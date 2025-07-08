package dev.babu.karat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * You are running a classroom and suspect that some of your students are passing around the answer to a
 * multiple-choice question in 2D grids of letters. The word may start anywhere in the grid, and consecutive
 * letters can be either immediately below or immediately to the right of the previous letter.
 *
 * Given a grid and a word, write a function that returns the location of the word in the grid as a
 * list of coordinates. If there are multiple matches, return any one.
 *
 * grid1 = [
 *     ['b', 'b', 'b', 'a', 'l', 'l', 'o', 'o'],
 *     ['b', 'a', 'c', 'c', 'e', 's', 'c', 'n'],
 *     ['a', 'l', 't', 'e', 'w', 'c', 'e', 'w'],
 *     ['a', 'l', 'o', 's', 's', 'e', 'c', 'c'],
 *     ['w', 'o', 'o', 'w', 'a', 'c', 'a', 'w'],
 *     ['i', 'b', 'w', 'o', 'w', 'w', 'o', 'w']
 * ]
 * word1_1 = "access"      # [(1, 1), (1, 2), (1, 3), (2, 3), (3, 3), (3, 4)]
 * word1_2 = "balloon"     # [(0, 2), (0, 3), (0, 4), (0, 5), (0, 6), (0, 7), (1, 7)]
 *
 * word1_3 = "wow"         # [(4, 3), (5, 3), (5, 4)] OR
 *                         # [(5, 2), (5, 3), (5, 4)] OR
 *                         # [(5, 5), (5, 6), (5, 7)]
 *
 * word1_4 = "sec"         # [(3, 4), (3, 5), (3, 6)] OR
 *                         # [(3, 4), (3, 5), (4, 5)]
 *
 * word1_5 = "bbaal"       # [(0, 0), (1, 0), (2, 0), (3, 0), (3, 1)]
 *
 *
 * grid2 = [
 *   ['a'],
 * ]
 * word2_1 = "a"
 *
 * grid3 = [
 *     ['c', 'a'],
 *     ['t', 't'],
 *     ['h', 'a'],
 *     ['a', 'c'],
 *     ['t', 'g']
 * ]
 * word3_1 = "cat"
 * word3_2 = "hat"
 *
 * grid4 = [
 *     ['c', 'c', 'x', 't', 'i', 'b'],
 *     ['c', 'a', 't', 'n', 'i', 'i'],
 *     ['a', 'x', 'n', 'x', 'p', 't'],
 *     ['t', 'x', 'i', 'x', 't', 't']
 * ]
 * word4_1 = "catnip"      # [(1, 0), (1, 1), (1, 2), (1, 3), (1, 4), (2, 4)] OR
 *                         # [(0, 1), (1, 1), (1, 2), (1, 3), (1, 4), (2, 4)]
 *
 *
 * All test cases:
 *
 * search(grid1, word1_1) => [(1, 1), (1, 2), (1, 3), (2, 3), (3, 3), (3, 4)]
 * search(grid1, word1_2) => [(0, 2), (0, 3), (0, 4), (0, 5), (0, 6), (0, 7), (1, 7)]
 * search(grid1, word1_3) => [(4, 3), (5, 3), (5, 4)] OR
 *                           [(5, 2), (5, 3), (5, 4)] OR
 *                           [(5, 5), (5, 6), (5, 7)]
 * search(grid1, word1_4) => [(3, 4), (3, 5), (3, 6)] OR
 *                           [(3, 4), (3, 5), (4, 5)]
 * search(grid1, word1_5) => [(0, 0), (1, 0), (2, 0), (3, 0), (3, 1)]
 *
 * search(grid2, word2_1) => [(0, 0)]
 *
 * search(grid3, word3_1) => [(0, 0), (0, 1), (1, 1)]
 * search(grid3, word3_2) => [(2, 0), (3, 0), (4, 0)]
 *
 * search(grid4, word4_1) => [(1, 0), (1, 1), (1, 2), (1, 3), (1, 4), (2, 4)] OR
 *                           [(0, 1), (1, 1), (1, 2), (1, 3), (1, 4), (2, 4)]
 *
 * Complexity analysis variables:
 *
 * r = number of rows
 * c = number of columns
 * w = length of the word
 */
public class SearchWordInGridDFS {

    static List<int[]> search(char[][] grid, String word) {
        int rows = grid.length;
        int columns = grid[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                List<int[]> path = new ArrayList<>();
                if (dfs(grid, word, 0, i, j, path)) {
                    return path;
                }
            }
        }
        return Collections.emptyList();
    }

    static boolean dfs(char[][] grid, String word, int index, int i, int j, List<int[]> path) {
        int rows = grid.length;
        int columns = grid[0].length;

        if (i < 0 || j < 0 || i >= rows || j >= columns) {
            return false;
        }

        if (grid[i][j] != word.charAt(index)) {
            return false;
        }

        path.add(new int[] {i, j});

        if (index == word.length() - 1) {
            return true;
        }

        // Try right
        if (dfs(grid, word, index + 1, i, j + 1, path)) {
            return true;
        }

        // Try down
        if (dfs(grid, word, index + 1, i + 1, j, path)) {
            return true;
        }

        // Backtrack if not found
        path.removeLast();
        return false;
    }

    // Helper to print result
    static void printResult(List<int[]> result) {
        for (int[] cell : result) {
            System.out.print("(" + cell[0] + ", " + cell[1] + ") ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        char[][] grid1 = {
                {'b', 'b', 'b', 'a', 'l', 'l', 'o', 'o'},
                {'b', 'a', 'c', 'c', 'e', 's', 'c', 'n'},
                {'a', 'l', 't', 'e', 'w', 'c', 'e', 'w'},
                {'a', 'l', 'o', 's', 's', 'e', 'c', 'c'},
                {'w', 'o', 'o', 'w', 'a', 'c', 'a', 'w'},
                {'i', 'b', 'w', 'o', 'w', 'w', 'o', 'w'},
        };

        printResult(search(grid1, "access"));   // [(1, 1), (1, 2), (1, 3), (2, 3), (3, 3), (3, 4)]
        printResult(search(grid1, "balloon"));  // [(0, 2), (0, 3), (0, 4), (0, 5), (0, 6), (0, 7), (1, 7)]
        printResult(search(grid1, "wow"));      // One of the valid paths
        printResult(search(grid1, "sec"));      // One of the valid paths
        printResult(search(grid1, "bbaal"));    // [(0, 0), (1, 0), (2, 0), (3, 0), (3, 1)]

        char[][] grid2 = {
                {'a'},
        };
        printResult(search(grid2, "a"));        // [(0, 0)]

        char[][] grid3 = {
                {'c', 'a'},
                {'t', 't'},
                {'h', 'a'},
                {'a', 'c'},
                {'t', 'g'},
        };
        printResult(search(grid3, "cat"));      // [(0, 0), (0, 1), (1, 1)]
        printResult(search(grid3, "hat"));      // [(2, 0), (3, 0), (4, 0)]

        char[][] grid4 = {
                {'c', 'c', 'x', 't', 'i', 'b'},
                {'c', 'a', 't', 'n', 'i', 'i'},
                {'a', 'x', 'n', 'x', 'p', 't'},
                {'t', 'x', 'i', 'x', 't', 't'},
        };
        printResult(search(grid4, "catnip"));   // One of the valid paths
    }
}
