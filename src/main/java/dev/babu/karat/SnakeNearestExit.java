package dev.babu.karat;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
We have a two-dimensional board game involving snakes. The board has two types of squares on it: +'s represent
 impassable squares where snakes cannot go, and 0's represent squares through which snakes can move. Snakes may move in
 any of four directions - up, down, left, or right - one square at a time, but they will never return to a square that
 they've already visited.  If a snake enters the board on an edge square, we want to catch it at a different exit square
 on the board's edge.

The snake is familiar with the board and will take the route to the nearest reachable exit, in terms of the number of
 squares it has to move through to get there. Note that there may not be a reachable exit.

Here is an example board:

    col-->        0  1  2  3  4  5  6  7  8
               +---------------------------
    row      0 |  +  +  +  +  +  +  +  0  0
     |       1 |  +  +  0  0  0  0  0  +  +
     |       2 |  0  0  0  0  0  +  +  0  +
     v       3 |  +  +  0  +  +  +  +  0  0
             4 |  +  +  0  0  0  0  0  0  +
             5 |  +  +  0  +  +  0  +  0  +

Write a function that takes a rectangular board with only +'s and O's, along with a starting point on the edge of the
 board, and returns the coordinates of the nearest exit to which it can travel. If there is a tie, return any of the
nearest exits.
-----------------------------------------------------
Sample inputs:
board1 = [['+', '+', '+', '+', '+', '+', '+', '0', '0'],
          ['+', '+', '0', '0', '0', '0', '0', '+', '+'],
          ['0', '0', '0', '0', '0', '+', '+', '0', '+'],
          ['+', '+', '0', '+', '+', '+', '+', '0', '0'],
          ['+', '+', '0', '0', '0', '0', '0', '0', '+'],
          ['+', '+', '0', '+', '+', '0', '+', '0', '+']]
start1_1 = (2, 0) # Expected output = (5, 2)
start1_2 = (0, 7) # Expected output = (0, 8)
start1_3 = (5, 2) # Expected output = (2, 0) or (5, 5)
start1_4 = (5, 5) # Expected output = (5, 7)

board2 = [['+', '+', '+', '+', '+', '+', '+'],
          ['0', '0', '0', '0', '+', '0', '+'],
          ['+', '0', '+', '0', '+', '0', '0'],
          ['+', '0', '0', '0', '+', '+', '+'],
          ['+', '+', '+', '+', '+', '+', '+']]
start2_1 = (1, 0) # Expected output = null (or a special value
representing no possible exit)
start2_2 = (2, 6) # Expected output = null

board3 = [['+', '0', '+', '0', '+',],
          ['0', '0', '+', '0', '0',],
          ['+', '0', '+', '0', '+',],
          ['0', '0', '+', '0', '0',],
          ['+', '0', '+', '0', '+']]
start3_1 = (0, 1) # Expected output = (1, 0)
start3_2 = (4, 1) # Expected output = (3, 0)
start3_3 = (0, 3) # Expected output = (1, 4)
start3_4 = (4, 3) # Expected output = (3, 4)

board4 = [['+', '0', '+', '0', '+',],
          ['0', '0', '0', '0', '0',],
          ['+', '+', '+', '+', '+',],
          ['0', '0', '0', '0', '0',],
          ['+', '0', '+', '0', '+']]
start4_1 = (1, 0) # Expected output = (0, 1)
start4_2 = (1, 4) # Expected output = (0, 3)
start4_3 = (3, 0) # Expected output = (4, 1)
start4_4 = (3, 4) # Expected output = (4, 3)

board5 =  [['+', '0', '0', '0', '+',],
           ['+', '0', '+', '0', '0',],
           ['+', '0', '0', '0', '0',],
           ['+', '0', '0', '0', '+']]
start5_1 = (0, 1) # Expected output = (0, 2)
start5_2 = (3, 1) # Expected output = (3, 2)
start5_3 = (1, 4) # Expected output = (2, 4)

board6 = [['+', '+', '+', '+', '+', '+', '+', '+'],
          ['0', '0', '0', '0', '0', '0', '0', '0'],
          ['+', '0', '0', '0', '0', '0', '0', '0'],
          ['+', '0', '0', '0', '0', '0', '0', '+'],
          ['0', '0', '0', '0', '0', '0', '0', '+'],
          ['+', '+', '+', '+', '+', '+', '0', '+']]

start6_1 = (4,0) # Expected output = (1, 0)

All test cases:
findExit(board1, start1_1) => (5, 2)
findExit(board1, start1_2) => (0, 8)
findExit(board1, start1_3) => (2, 0) or (5, 5)
findExit(board1, start1_4) => (5, 7)
findExit(board2, start2_1) => null (or a special value representing
no possible exit)
findExit(board2, start2_2) => null
findExit(board3, start3_1) => (1, 0)
findExit(board3, start3_2) => (3, 0)
findExit(board3, start3_3) => (1, 4)
findExit(board3, start3_4) => (3, 4)
findExit(board4, start4_1) => (0, 1)
findExit(board4, start4_2) => (0, 3)
findExit(board4, start4_3) => (4, 1)
findExit(board4, start4_4) => (4, 3)
findExit(board5, start5_1) => (0, 2)
findExit(board5, start5_2) => (3, 2)
findExit(board5, start5_3) => (2, 4)
findExit(board6, start6_1) => (1, 0)

Complexity Analysis:

r: number of rows in the board
c: number of columns in the board

*/

public class SnakeNearestExit {

    private static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public static int[] findNearestExit(char[][] board, int[] startPosition) {
        int rows = board.length;
        int columns = board[0].length;

        boolean[][] visited = new boolean[rows][columns];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {startPosition[0], startPosition[1], 0});
        visited[startPosition[0]][startPosition[1]] = true;

        int[] result = new int[] {-1, -1};
        int minimumDistance = Integer.MAX_VALUE;

        while(!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0], column = current[1], distance = current[2];

            // Check if it's a valid exit (edge cell, not the starting point)
            if ((row != startPosition[0] || column != startPosition[1]) &&
                    isOnEdge(row, column, rows, columns)) {
                if (distance < minimumDistance ||
                        (distance == minimumDistance && (row < result[0] ||
                                (row == result[0] && column < result[1])))) {
                    result[0] = row;
                    result[1] = column;
                    minimumDistance = distance;
                }
            }

            for (int[] dir : DIRS) {
                int nextRow = row + dir[0];
                int nextColumn = column + dir[1];
                if (isValidMove(nextRow, nextColumn, board, visited)) {
                    visited[nextRow][nextColumn] = true;
                    queue.add(new int[] {nextRow, nextColumn, distance + 1});
                }
            }

        }
        return (result[0] == -1 && result[1] == -1) ? null : result;
    }

    private static boolean isOnEdge(int row, int column, int rows, int columns) {
        return row == 0 || column == 0 || row == rows - 1 || column == columns - 1;
    }

    private static boolean isValidMove(int row, int column, char[][] board, boolean[][] visited) {
        return row >= 0 && row < board.length && column >= 0 && column < board[0].length
                && board[row][column] == '0' && !visited[row][column];
    }

    private static void printBoard(char[][] board) {
        System.out.println("Board Layout:");
        System.out.print("   ");
        for (int col = 0; col < board[0].length; col++) {
            System.out.print(col + " ");
        }
        System.out.println();

        for (int row = 0; row < board.length; row++) {
            System.out.print(row + ": ");
            for (int col = 0; col < board[row].length; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        char[][] board1 = {
                {'+', '+', '+', '+', '+', '+', '+', '0', '0'},
                {'+', '+', '0', '0', '0', '0', '0', '+', '+'},
                {'0', '0', '0', '0', '0', '+', '+', '0', '+'},
                {'+', '+', '0', '+', '+', '+', '+', '0', '0'},
                {'+', '+', '0', '0', '0', '0', '0', '0', '+'},
                {'+', '+', '0', '+', '+', '0', '+', '0', '+'}
        };
        printBoard(board1);
        System.out.println("Nearest exit to [2, 0] is "
                + Arrays.toString(findNearestExit(board1, new int[] {2, 0}))); // (5, 2)
        System.out.println("Nearest exit to [0, 7] is "
                + Arrays.toString(findNearestExit(board1, new int[] {0, 7}))); // (0, 8)
        System.out.println("Nearest exit to [5, 2] is "
                + Arrays.toString(findNearestExit(board1, new int[] {5, 2}))); // (2, 0) or (5, 5)
        System.out.println("Nearest exit to [5, 5] is "
                + Arrays.toString(findNearestExit(board1, new int[] {5, 5}))); // (5, 7)


        char[][] board2 = {
                {'+', '+', '+', '+', '+', '+', '+'},
                {'0', '0', '0', '0', '+', '0', '+'},
                {'+', '0', '+', '0', '+', '0', '0'},
                {'+', '0', '0', '0', '+', '+', '+'},
                {'+', '+', '+', '+', '+', '+', '+'}
        };
        printBoard(board2);
        System.out.println("Nearest exit to [1, 0] is "
                + Arrays.toString(findNearestExit(board2, new int[] {1, 0}))); // null
        System.out.println("Nearest exit to [2, 6] is "
                + Arrays.toString(findNearestExit(board2, new int[] {2, 6}))); // null

        char[][] board3 = {
                {'+', '0', '+', '0', '+'},
                {'0', '0', '+', '0', '0'},
                {'+', '0', '+', '0', '+'},
                {'0', '0', '+', '0', '0'},
                {'+', '0', '+', '0', '+'}
        };
        printBoard(board3);
        System.out.println("Nearest exit to [0, 1] is "
                + Arrays.toString(findNearestExit(board3, new int[] {0, 1}))); // (1, 0)
        System.out.println("Nearest exit to [4, 1] is "
                + Arrays.toString(findNearestExit(board3, new int[] {4, 1}))); // (3, 0)
        System.out.println("Nearest exit to [0, 3] is "
                + Arrays.toString(findNearestExit(board3, new int[] {0, 3}))); // (1, 4)
        System.out.println("Nearest exit to [4, 3] is "
                + Arrays.toString(findNearestExit(board3, new int[] {4, 3}))); // (3, 4)

        char[][] board4 = {
                {'+', '0', '+', '0', '+'},
                {'0', '0', '0', '0', '0'},
                {'+', '+', '+', '+', '+'},
                {'0', '0', '0', '0', '0'},
                {'+', '0', '+', '0', '+'}
        };
        printBoard(board4);
        System.out.println("Nearest exit to [1, 0] is "
                + Arrays.toString(findNearestExit(board4, new int[] {1, 0}))); // (0, 1)
        System.out.println("Nearest exit to [1, 4] is "
                + Arrays.toString(findNearestExit(board4, new int[] {1, 4}))); // (0, 3)
        System.out.println("Nearest exit to [3, 0] is "
                + Arrays.toString(findNearestExit(board4, new int[] {3, 0}))); // (4, 1)
        System.out.println("Nearest exit to [3, 4] is "
                + Arrays.toString(findNearestExit(board4, new int[] {3, 4}))); // (4, 3)

        char[][] board5 = {
                {'+', '0', '0', '0', '+'},
                {'+', '0', '+', '0', '0'},
                {'+', '0', '0', '0', '0'},
                {'+', '0', '0', '0', '+'}
        };
        printBoard(board5);
        System.out.println("Nearest exit to [0, 1] is "
                + Arrays.toString(findNearestExit(board5, new int[] {0, 1}))); // (0, 2)
        System.out.println("Nearest exit to [3, 1] is "
                + Arrays.toString(findNearestExit(board5, new int[] {3, 1}))); // (3, 2)
        System.out.println("Nearest exit to [1, 4] is "
                + Arrays.toString(findNearestExit(board5, new int[] {1, 4}))); // (2, 4)

        char[][] board6 = {
                {'+', '+', '+', '+', '+', '+', '+', '+'},
                {'0', '0', '0', '0', '0', '0', '0', '0'},
                {'+', '0', '0', '0', '0', '0', '0', '0'},
                {'+', '0', '0', '0', '0', '0', '0', '+'},
                {'0', '0', '0', '0', '0', '0', '0', '+'},
                {'+', '+', '+', '+', '+', '+', '0', '+'}
        };
        printBoard(board6);
        System.out.println("Nearest exit to [4, 0] is "
                + Arrays.toString(findNearestExit(board6, new int[] {4, 0}))); // (1, 0)
    }
}
