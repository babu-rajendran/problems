package dev.babu.amazon;

/**
 * The Amazon distribution center consists of arrays of products, each possessing unique attributes. The task at hand is
 * to compute the beauty of these product arrays, with the goal of achieving an efficient selection process.
 *
 * More specifically, there are arrays of products, and each array corresponds to a list of attributes. The beauty of a
 * subarray B = [products[l], products[l+1], ..., products[r]] is quantified by counting the indices i that satisfy
 * these conditions:
 *
 * l ≤ i ≤ r
 * for every index j such that i < j ≤ r, products[i] > products[j]
 * An array B is a subarray of an array A if B can be obtained from A by deletion of several (possibly, zero or all)
 * elements from the beginning and several (possibly, zero or all) elements from the end. In particular, an array is a
 * subarray of itself.
 *
 * The beautiness of the entire array of products is determined by the sum of beauty values across all subarrays of a
 * given size k.
 *
 * Given an array products of size n and an integer k. Compute the total beautiness of the array of products.
 *
 * Function Description
 *
 * Complete the function computeBeauty in the editor.
 *
 * computeBeauty has the following parameters:
 *
 * 1. int[] products: an array of integers representing the products
 * 2. int k: the size of the subarray
 * Returns
 *
 * int: the total beautiness of the array of products
 *
 * Example 1:
 * Input:  products = [3, 6, 2, 9, 4, 1], k = 3
 * Output: 8
 * So, the beauty of the array numbers is 2 + 1 + 2 + 3 = 8.
 */
public class ProductBeautyCalculator {

    public static int computeBeauty(int[] products, int k) {
        int n = products.length;
        int totalBeauty = 0;

        // Iterate over all subarrays of size k
        for (int start = 0; start <= n - k; start++) {
            int beauty = 0;

            // Iterate over each index in the current subarray
            for (int i = start; i < start + k; i++) {
                boolean isBeautiful = true;

                // Check if products[i] is greater than all elements to its right in the subarray
                for (int j = i + 1; j < start + k; j++) {
                    if (products[i] <= products[j]) {
                        isBeautiful = false;
                        break;
                    }
                }

                if (isBeautiful) {
                    beauty++;
                }
            }

            totalBeauty += beauty;
        }

        return totalBeauty;
    }

    public static void main(String[] args) {
        int[] products = {3, 6, 2, 9, 4, 1};
        int k = 3;
        int result = computeBeauty(products, k);
        System.out.println("Total Beautiness: " + result); // Expected Output: 8
    }
}
