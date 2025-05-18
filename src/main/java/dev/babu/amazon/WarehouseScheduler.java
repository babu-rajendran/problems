package dev.babu.amazon;

import java.util.PriorityQueue;

/**
 * You are managing operations at a large Amazon warehouse. Loaded trucks arrive at the warehouse sequentially and must
 * be unloaded within a specific timeframe to ensure timely delivery. Your task is to determine the minimum number of
 * dock bays needed to unload all trucks within the given timeframe.
 *
 * Formally, given a scheduling array truckCargoSize of length n, where each unit in the array represents the amount of
 * time in minutes that a dock bay will take to unload the cargo from the ith truck, and a second integer,
 * maxTurnaroundTime, representing the total allowed time to unload trucks, find the smallest number of dock bays d that
 * will enable you to unload all trucks within maxTurnaroundTime minutes.
 *
 * Notes:
 *
 * As soon as a dock bay becomes available after unloading a truck, it can immediately start processing the next truck.
 * It is guaranteed that unloading all trucks is possible with some number of dock bays.
 * Only the start times of unloading need to be considered in order, not the finish times.
 * Function Description
 *
 * Complete the function getMinimumDockBays in the editor.
 *
 * getMinimumDockBays has the following parameters:
 *
 * int truckCargoSize[n]: the time in minutes for the dock bay to unload cargo from the ith truck
 * long maxTurnaroundTime: the maximum allowed total unloading time.
 * Returns
 *
 * int: the smallest number of dock bays d to unload all the cargo within maxTurnaroundTime minutes.
 *
 * Example 1:
 *
 * Input:  truckCargoSize = [3, 4, 3, 2, 3], maxTurnaroundTime = 8
 * Output: 3
 * Explanation:
 *
 * Attemping with two dock bays (d = 2):
 *
 * Truck Processing Timeline for 2 Dock1 Bays:
 * With two dock bays, all trucks unloaded in 9 minutes, which exceeds the maxTurnaroundTime of 8 minutes:
 *
 * Attempting with three dock bays (d = 3):
 *
 * With three dock bays, all trucks are unloaded in 6 minutes, which is within the maxTurnaroundTime of 8 minutes.
 *
 * So, the minimum number of dock bays required to unload all trucks within 8 minutes is 3.
 *
 */

public class WarehouseScheduler {

    public static int getMinimumDockBays(int[] truckCargoSize, long maxTurnaroundTime) {
        int low = 1;
        int high = truckCargoSize.length;
        int result = high;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (canUnloadAll(truckCargoSize, maxTurnaroundTime, mid)) {
                result = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return result;
    }

    private static boolean canUnloadAll(int[] cargo, long maxTime, int docks) {
        PriorityQueue<Long> minHeap = new PriorityQueue<>();

        for (int i = 0; i < docks; i++) {
            minHeap.offer(0L);
        }

        for (int time : cargo) {
            long earliestAvailable = minHeap.poll();
            minHeap.offer(earliestAvailable + time);
        }

        while (minHeap.size() > 1) {
            minHeap.poll();
        }

        long totalTime = minHeap.poll();
        return totalTime <= maxTime;

    }

    public static void main(String[] args) {
        int[] truckCargoSize1 = {3, 4, 3, 2, 3};
        long maxTurnaroundTime1 = 8;
        System.out.println(getMinimumDockBays(truckCargoSize1, maxTurnaroundTime1));  // Output: 3

        int[] truckCargoSize2 = {2, 3, 1};
        long maxTurnaroundTime2 = 7;
        System.out.println(getMinimumDockBays(truckCargoSize2, maxTurnaroundTime2));  // Output: 1
    }
}
