package dev.babu.amazon;

import java.util.*;

/**
 * The Amazon Alexa development team needs to analyze request logs across numSkills different Alexa skills to understand
 * their performance and user engagement.
 *
 * The skills are indexed from 1 to numSkills, and the logs are provided as a 2D array requestLog of size m where
 * requestLog[i] = [skill_ID, timeStamp] represents a request made at timeStamp to the skill with ID skill_ID.
 *
 * You are given an integer numSkills, a 2D integer array requestLogs, an integer timeWindow (representing a lookback
 * period), and an array of queryTimes containing q queries.
 *
 * For each queryTime[i] determine the number of skills that did not receive a request in the time interval
 * [queryTime[i] - timeWindow, queryTime[i]]. Return an array of length q containing the result of each of the query.
 *
 * Note: If for some query in the numSkills received requestLog the given time interval for that query, then answer is 0.
 *
 * Function Description
 *
 * Complete the function findIdleSkillsQuery in the editor.
 *
 * findIdleSkillsQuery has the following parameters:
 *
 * 1. int numSkills: an integer denoting the number of skills
 * 2. int[][] requestLogs: 2D array denoting the request logs
 * 3. int[] queryTime: an integer array denoting the query times
 * 4. int timeWindow: an integer denoting the lookback period for queries
 * Returns
 *
 * int[]: an integer array denoting the answers to the queries
 *
 * Example 1:
 *
 * Input:  numSkills = 3, requestLogs = [[1, 3], [2, 6], [1, 5]], queryTime = [10, 11], timeWindow = 5
 * Output: [1, 2]
 * Explanation:
 *
 * For queryTime[0] = 10, skills 1 and 2 had requests at timeStamps 5 and 6, respectively,  which fall in the interval
 * [5, 10]. Skill 3 did not receive any requests in the given interval. Thus, answer for this queryTime is 1.
 *
 * For queryTime[1] = 11, skill 2 requests at timeStamp6, respectively, which fall in the interval [6, 11]. Skill 1 and
 * 3 did not receive any requests in the given interval. Thus, answer for this queryTime is 2.
 *
 * So, our answer turns out to be [1, 2].
 *
 */

public class IdleSkills {

    public static int[] findIdleSkillsQuery(int numSkills, int[][] requestLogs, int[] queryTime, int timeWindow) {
        // write your code here
        // Map to store each skill's request timestamps in a sorted list
        Map<Integer, List<Integer>> skillToTimestamps = new HashMap<>();

        for (int[] log : requestLogs) {
            int skillId = log[0];
            int timestamp = log[1];

            skillToTimestamps
                    .computeIfAbsent(skillId, k -> new ArrayList<>())
                    .add(timestamp);
        }

        // Sort timestamps for each skill for binary search
        for (List<Integer> timestamps : skillToTimestamps.values()) {
            Collections.sort(timestamps);
        }

        int[] result = new int[queryTime.length];

        for (int i = 0; i < queryTime.length; i++) {
            int query = queryTime[i];
            int start = query - timeWindow;

            int idleCount = 0;

            for (int skill = 1; skill <= numSkills; skill++) {
                List<Integer> timestamps = skillToTimestamps.getOrDefault(skill, new ArrayList<>());

                if (!hasTimestampInWindow(timestamps, start, query)) {
                    idleCount++;
                }
            }

            result[i] = idleCount;
        }
        return result;
    }

    private static boolean hasTimestampInWindow(List<Integer> timestamps, int start, int end) {
        if (timestamps.isEmpty()) return false;

        int left = 0, right = timestamps.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int time = timestamps.get(mid);

            if (time >= start && time <= end) {
                return true;
            } else if (time < start) {
                left = mid + 1;
            } else { // time > end
                right = mid - 1;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        // numSkills = 3, requestLogs = [[1, 3], [2, 6], [1, 5]], queryTime = [10, 11], timeWindow = 5
        // Output: [1, 2]
        System.out.println(Arrays.toString(
                findIdleSkillsQuery(
                        3,
                        new int[][]{{1, 3}, {2, 6}, {1, 5}},
                        new int[]{10, 11},
                        5)));

        // numSkills = 6, requestLogs = [[3, 2], [4, 3], [2, 6], [6, 3]], queryTime = [3, 2, 6], timeWindow = 2
        // Output: [3, 5, 5]
        System.out.println(Arrays.toString(
                findIdleSkillsQuery(
                        6,
                        new int[][]{{3, 2}, {4, 3}, {2, 6}, {6, 3}},
                        new int[]{3, 2, 6},
                        2)));

        // numSkills = 6, requestLogs = [[3, 2], [4, 3], [2, 6], [6, 3]], queryTime = [1, 2, 3, 4, 5, 6], timeWindow = 1
        // Output: [6, 5, 3, 4, 6, 5]
        System.out.println(Arrays.toString(
                findIdleSkillsQuery(
                        6,
                        new int[][]{{3, 2}, {4, 3}, {2, 6}, {6, 3}},
                        new int[]{1, 2, 3, 4, 5, 6},
                        1)));
    }
}
