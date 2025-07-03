package dev.babu.karat;

import java.util.*;

public class FriendCycle {

    /**
     * Given employees and friendships, find all adjacencies that denote the friendship, A
     * friendship is bi-directional/mutual so if 1 is friends with 2, 2 is also friends with 1.
     *
     * Sample Input:
     * employees = [
     *   "1, Bill, Engineer",
     *   "2, Joe, HR",
     *   "3, Sally, Engineer",
     *   "4, Richard, Business",
     *   "6, Tom, Engineer"
     * ]
     *
     * friendships = [
     *   "1, 2",
     *   "1, 3",
     *   "3, 4"
     * ]
     *
     * Sample Output:
     * 1: 2, 3
     * 2: 1
     * 3: 1, 4
     * 4: 3
     * 6: None
     *
     */
    Map<String, List<String>> buildFriendshipAdjacency(List<String> employees, List<String> friendships) {
        Map<String, List<String>> adjacency = new HashMap<>();

        // Step 1: Initialize all employee IDs with empty friend lists
        for (String emp : employees) {
            String empId = emp.split(",")[0].trim();
            adjacency.put(empId, new ArrayList<>());
        }

        // Step 2: Add friendships in both directions
        for (String relation : friendships) {
            String[] parts = relation.split(",");
            String a = parts[0].trim();
            String b = parts[1].trim();

            // Add b to a's list
            adjacency.get(a).add(b);
            // Add a to b's list
            adjacency.get(b).add(a);
        }

        return adjacency;
    }

    /**
     * Now for each department count the number of employees that have a friend in
     * another department.
     *
     * Sample Output:
     * Output:
     * "Engineer: 2 of 3"
     * "HR: 1 of 1"
     * "Business: 1 of 1"
     *
     */
    void hasOtherDepartmentFriends() {
        List<String> employees = Arrays.asList(
                "1, Bill, Engineer",
                "2, Joe, HR",
                "3, Sally, Engineer",
                "4, Richard, Business",
                "6, Tom, Engineer"
        );

        List<String> friendships = Arrays.asList(
                "1, 2",
                "1, 3",
                "3, 4"
        );

        // Step 1: Build employeeId → department map
        Map<String, String> empToDept = new HashMap<>();
        // Step 2: Build department → list of employeeIds map
        Map<String, List<String>> deptToEmps = new HashMap<>();

        for (String emp : employees) {
            String[] parts = emp.split(",");
            String id = parts[0].trim();
            String name = parts[1].trim();
            String dept = parts[2].trim();

            empToDept.put(id, dept);
            deptToEmps.putIfAbsent(dept, new ArrayList<>());
            deptToEmps.get(dept).add(id);
        }

        // Step 3: Build friendship adjacency list
        Map<String, List<String>> adjacency = new HashMap<>();
        for (String id : empToDept.keySet()) {
            adjacency.put(id, new ArrayList<>());
        }

        for (String relation : friendships) {
            String[] parts = relation.split(",");
            String a = parts[0].trim();
            String b = parts[1].trim();
            adjacency.get(a).add(b);
            adjacency.get(b).add(a);
        }

        // Step 4: Track which employees have friends in other departments
        Set<String> hasCrossDeptFriend = new HashSet<>();
        for (String empId : adjacency.keySet()) {
            String dept = empToDept.get(empId);
            for (String friendId : adjacency.get(empId)) {
                if (!empToDept.get(friendId).equals(dept)) {
                    hasCrossDeptFriend.add(empId);
                    break; // Only need at least one friend in another department
                }
            }
        }

        // Step 5: Print result
        for (String dept : deptToEmps.keySet()) {
            List<String> emps = deptToEmps.get(dept);
            int total = emps.size();
            int count = 0;
            for (String empId : emps) {
                if (hasCrossDeptFriend.contains(empId)) {
                    count++;
                }
            }
            System.out.println(dept + ": " + count + " of " + total);
        }
    }

    public static void main(String[] args) {
        FriendCycle friendCycle = new FriendCycle();
        List<String> employees = Arrays.asList(
                "1, Bill, Engineer",
                "2, Joe, HR",
                "3, Sally, Engineer",
                "4, Richard, Business",
                "6, Tom, Engineer"
        );

        List<String> friendships = Arrays.asList(
                "1, 2",
                "1, 3",
                "3, 4"
        );

        Map<String, List<String>> adjacencyList = friendCycle.buildFriendshipAdjacency(employees, friendships);

        // Print the result
        for (String empId : adjacencyList.keySet()) {
            System.out.println(empId + ": " + adjacencyList.get(empId));
        }

        friendCycle.hasOtherDepartmentFriends();
    }
}
