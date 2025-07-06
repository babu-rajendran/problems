package dev.babu.karat;

/**
 * We are writing an application for calculating garden scores in this year's garden contest. All gardens have a
 * rectangular shape. Within a garden, all rows have the same number of cells.
 *
 * This year's committee decided to use the following criteria for scoring gardens:
 * - Identify the largest square block of the same flower.
 * - Add 1 point for every cell in that block.
 *
 * Example:
 * ______________________________________________________
 * | Iris | Iris | Iris  | Iris  | Iris  | Iris  | Iris |
 * ------------------------------------------------------
 * | Iris | Iris | *Rose | *Rose | *Rose | *Rose | Lily |
 * ------------------------------------------------------
 * | Iris | Iris | *Rose | *Rose | *Rose | *Rose | Lily |
 * ------------------------------------------------------
 * | Iris | Iris | *Rose | *Rose | *Rose | *Rose | Lily |
 * ------------------------------------------------------
 * | Iris | Iris | *Rose | *Rose | *Rose | *Rose | Lily |
 * ------------------------------------------------------
 * | Iris | Iris | Sage  | Sage  | Sage  | Sage  | Lily |
 * ------------------------------------------------------
 *
 * The largest square block of the same flower (Rose) has 16 cells, so the above garden's score is 16.
 *
 * Write a function that accepts a garden and returns the total score.
 *
 *
 * Test cases:
 * garden_1 = [
 *     ["Iris", "Iris", "Iris", "Iris", "Iris", "Iris", "Iris"],
 *     ["Iris", "Iris", "Rose", "Rose", "Rose", "Rose", "Lily"],
 *     ["Iris", "Iris", "Rose", "Rose", "Rose", "Rose", "Lily"],
 *     ["Iris", "Iris", "Rose", "Rose", "Rose", "Rose", "Lily"],
 *     ["Iris", "Iris", "Rose", "Rose", "Rose", "Rose", "Lily"],
 *     ["Iris", "Iris", "Sage", "Sage", "Sage", "Sage", "Lily"]
 * ]
 *
 * garden_2 = [
 *     ["Larch", "Holly", "Holly", "Heath", "Holly", "Heath"],
 *     ["Heath", "Pansy", "Holly", "Pansy", "Aspen", "Aspen"],
 *     ["Pansy", "Pansy", "Larch", "Lilac", "Aspen", "Lilac"],
 *     ["Hazel", "Lilac", "Basil", "Lilac", "Lilac", "Larch"],
 *     ["Peony", "Hazel", "Basil", "Hazel", "Holly", "Basil"]
 * ]
 *
 * garden_3 = [
 *     ["Arum", "Dock", "Iris", "Lily", "Reed", "Rose", "Sage", "Woad"]
 * ]
 *
 * garden_4 = [
 *     ["Arum"],
 *     ["Dock"],
 *     ["Iris"],
 *     ["Lily"],
 *     ["Reed"],
 *     ["Rose"],
 *     ["Sage"],
 *     ["Woad"]
 * ]
 *
 * garden_5 = [
 *     ["Peony"]
 * ]
 *
 * garden_6 = [
 *     ["Ivy"  , "Rue"  , "Yew"  , "Arum" , "Dock" ],
 *     ["Iris" , "Lily" , "Reed" , "Rose" , "Sage" ],
 *     ["Woad" , "Aspen", "Basil", "Hazel", "Heath"],
 *     ["Holly", "Larch", "Lilac", "Pansy", "Peony"],
 * ]
 *
 * garden_7 = [
 *     ["Rose", "Rose", "Rose", "Rose", "Rose"],
 *     ["Rose", "Rose", "Rose", "Rose", "Rose"],
 *     ["Rose", "Rose", "Rose", "Rose", "Rose"],
 *     ["Rose", "Rose", "Rose", "Rose", "Rose"],
 *     ["Rose", "Rose", "Rose", "Rose", "Rose"]
 * ]
 *
 * garden_8 = [
 *     ["Iris", "Iris", "Iris"],
 *     ["Iris", "Iris", "Iris"],
 *     ["Iris", "Iris", "Rose"]
 * ]
 *
 * garden_9 = [
 * 	["Iris", "Iris", "Rose", "Rose", "Iris", "Iris", "Iris"],
 * 	["Iris", "Iris", "Rose", "Rose", "Rose", "Rose", "Lily"],
 * 	["Iris", "Iris", "Rose", "Rose", "Rose", "Rose", "Lily"],
 * 	["Iris", "Iris", "Rose", "Rose", "Rose", "Rose", "Lily"],
 * 	["Iris", "Iris", "Rose", "Rose", "Rose", "Rose", "Lily"],
 * 	["Iris", "Iris", "Sage", "Sage", "Sage", "Sage", "Lily"]
 * ]
 *
 * garden_10 = [
 * 	["Iris", "Iris", "Iris", "Iris", "Iris", "Iris", "Iris"],
 * 	["Iris", "Iris", "Rose", "Rose", "Rose", "Rose", "Rose"],
 * 	["Iris", "Iris", "Rose", "Rose", "Rose", "Rose", "Lily"],
 * 	["Iris", "Iris", "Rose", "Rose", "Rose", "Rose", "Lily"],
 * 	["Iris", "Iris", "Rose", "Rose", "Rose", "Rose", "Lily"],
 * 	["Iris", "Iris", "Sage", "Sage", "Sage", "Sage", "Lily"]
 * ]
 *
 * garden_11 = [
 * 	["Iris", "Iris", "Iris"],
 * 	["Iris", "Iris", "Iris"],
 * 	["Lily", "Iris", "Iris"]
 * ]
 *
 * calc_score(garden_1)       => 16
 * calc_score(garden_2)       => 1
 * calc_score(garden_3)       => 1
 * calc_score(garden_4)       => 1
 * calc_score(garden_5)       => 1
 * calc_score(garden_6)       => 1
 * calc_score(garden_7)       => 25
 * calc_score(garden_8)       => 4
 * calc_score(garden_9)       => 16
 * calc_score(garden_10)      => 16
 * calc_score(garden_11)      => 4
 *
 * Complexity variables:
 * r - the number of rows in the input garden
 * c - the number of cols in the input garden
 */
public class GardenScore {

    static int calcScore(String[][] garden) {
        if (garden == null || garden.length == 0 || garden[0].length == 0)
            return 0;

        int rows = garden.length;
        int cols = garden[0].length;
        int[][] dp = new int[rows][cols];

        int maxSize = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // First row or column => only 1 cell square is possible
                if (i == 0 || j == 0) {
                    dp[i][j] = 1;
                } else {
                    // Check if flower is same as top, left, and top-left
                    if (garden[i][j].equals(garden[i - 1][j]) &&
                            garden[i][j].equals(garden[i][j - 1]) &&
                            garden[i][j].equals(garden[i - 1][j - 1])) {

                        dp[i][j] = 1 + Math.min(dp[i - 1][j],
                                Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
                    } else {
                        dp[i][j] = 1;
                    }
                }

                // Keep track of the maximum square size
                maxSize = Math.max(maxSize, dp[i][j]);
            }
        }

        return maxSize * maxSize; // area = size²
    }

    public static void main(String[] args) {
        String[][] garden1 = {
                {"Iris", "Iris", "Iris", "Iris", "Iris", "Iris", "Iris"},
                {"Iris", "Iris", "Rose", "Rose", "Rose", "Rose", "Lily"},
                {"Iris", "Iris", "Rose", "Rose", "Rose", "Rose", "Lily"},
                {"Iris", "Iris", "Rose", "Rose", "Rose", "Rose", "Lily"},
                {"Iris", "Iris", "Rose", "Rose", "Rose", "Rose", "Lily"},
                {"Iris", "Iris", "Sage", "Sage", "Sage", "Sage", "Lily"}
        };

        System.out.println(calcScore(garden1)); // should print 16
    }
}
