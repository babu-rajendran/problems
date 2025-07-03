package dev.babu.karat;

import java.util.*;

public class BadgeAccess {

    /**
     * All employees who didn't use their badge while exiting the room
     *  – they recorded an enter without a matching exix
     * All employees who didn't use their badge while entering the room
     *  – they recorded an exit without a matching enter
     *
     * Sample Input and Output:
     *
     * badge_records = [
     *   ["Martha",   "exit"],
     *   ["Paul",     "enter"],
     *   ["Martha",   "enter"],
     *   ["Martha",   "exit"],
     *   ["Jennifer", "enter"],
     *   ["Paul",     "enter"],
     *   ["Curtis",   "enter"],
     *   ["Paul",     "exit"],
     *   ["Martha",   "enter"],
     *   ["Martha",   "exit"],
     *   ["Jennifer", "exit"],
     * ]
     * find_mismatched_entries(badge_records)
     * Expected output: ["Paul", "Curtis"], ["Martha"]
     *
     * Logic:
     * Iterate over the badge records.
     *
     * For each employee:
     *
     * If they enter, push it onto their personal stack.
     *
     * If they exit:
     *
     * If their stack is empty or last action is exit, it's a mismatched exit.
     *
     * If their last action was enter, pop the stack (it's a match).
     *
     * After processing, any remaining unmatched enter in the stack is a mismatched entry.
     *
     */
    public static void findMismatchedEntries(List<String[]> badgeRecords) {
        Set<String> missingExit = new HashSet<>();
        Set<String> missingEnter = new HashSet<>();
        Map<String, String> lastAction = new HashMap<>();

        for (String[] record : badgeRecords) {
            String name = record[0];
            String action = record[1];

            if (action.equals("enter")) {
                // If last action was also enter or no action yet, track for missing exit
                if ("enter".equals(lastAction.get(name))) {
                    missingExit.add(name);
                }
                lastAction.put(name, "enter");
            } else if (action.equals("exit")) {
                if (!"enter".equals(lastAction.get(name))) {
                    // No enter before exit or last action was also exit
                    missingEnter.add(name);
                } else {
                    lastAction.put(name, "exit");
                }
            }
        }

        // Final sweep: anyone left with last action 'enter' is missing an exit
        for (Map.Entry<String, String> entry : lastAction.entrySet()) {
            if ("enter".equals(entry.getValue())) {
                missingExit.add(entry.getKey());
            }
        }

        // Convert to sorted list for consistent output
        List<String> missingExitList = new ArrayList<>(missingExit);
        List<String> missingEnterList = new ArrayList<>(missingEnter);
        Collections.sort(missingExitList);
        Collections.sort(missingEnterList);

        System.out.println("Missing Exit: " + missingExitList);
        System.out.println("Missing Enter: " + missingEnterList);
    }

    /**
     * We want to find employees who badged into our secured room unusually often. We have an unordered list of names
     * and access times over a single day. Access times are given as three or four-digit numbers using 24-hour time,
     * such as "800" or "2250"
     * Write a function that finds anyone who badged into the room 3 or more times in a 1-hour period, and returns each
     * time that they badged in during that period. (If there are multiple 1-hour periods where this was true, just
     * return the first one.
     *
     * badge_records =
     *      ["Paul", 1355],
     *      ["Jennifer", 1910]
     *      ["John", 830]
     *      ["Paul", 1315]
     *      ["John", 835]
     *      ["Paul", 1405]
     *      ["Paul", 1630]
     *      ["John", 855],
     *
     *      ["John", 915]
     *      ["John", 930]
     *      ["Jennifer", 1335]
     *      ["Jennifer", 730]
     *      ["John", 1630]
     *      ]
     *
     * Expected output:
     * John: 830 835 855 915 930
     * Paul: 1315 1355 1405
     *
     */
    static void findFrequentBadgeAccesses(List<String[]> badgeRecords) {
        // Group badge times by person
        Map<String, List<Integer>> badgeMap = new HashMap<>();

        for (String[] record : badgeRecords) {
            String name = record[0];
            int time = Integer.parseInt(record[1]);
            badgeMap.computeIfAbsent(name, k -> new ArrayList<>()).add(time);
        }

        // Store result
        Map<String, List<Integer>> result = new HashMap<>();

        for (Map.Entry<String, List<Integer>> entry : badgeMap.entrySet()) {
            String name = entry.getKey();
            List<Integer> times = entry.getValue();
            Collections.sort(times);

            for (int i = 0; i < times.size(); i++) {
                List<Integer> window = new ArrayList<>();
                window.add(times.get(i));
                for (int j = i + 1; j < times.size(); j++) {
                    if (timeDiffInMinutes(times.get(i), times.get(j)) <= 60) {
                        window.add(times.get(j));
                        if (window.size() >= 3) {
                            result.put(name, window);
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if (result.containsKey(name)) {
                    break;
                }
            }
        }

        // Print result
        for (Map.Entry<String, List<Integer>> entry : result.entrySet()) {
            System.out.print(entry.getKey() + ": ");
            for (int time : entry.getValue()) {
                System.out.print(time + " ");
            }
            System.out.println();
        }
    }

    private static int timeDiffInMinutes(int t1, int t2) {
        int h1 = t1 / 100, m1 = t1 % 100;
        int h2 = t2 / 100, m2 = t2 % 100;
        return (h2 * 60 + m2) - (h1 * 60 + m1);
    }

    public static void main(String[] args) {
        List<String[]> badgeRecords = Arrays.asList(
                new String[]{"Martha", "exit"},
                new String[]{"Paul", "enter"},
                new String[]{"Martha", "enter"},
                new String[]{"Martha", "exit"},
                new String[]{"Jennifer", "enter"},
                new String[]{"Paul", "enter"},
                new String[]{"Curtis", "enter"},
                new String[]{"Paul", "exit"},
                new String[]{"Martha", "enter"},
                new String[]{"Martha", "exit"},
                new String[]{"Jennifer", "exit"}
        );

        findMismatchedEntries(badgeRecords);

        badgeRecords = Arrays.asList(
                new String[]{"Paul", "1355"},
                new String[]{"Jennifer", "1910"},
                new String[]{"John", "830"},
                new String[]{"Paul", "1315"},
                new String[]{"John", "835"},
                new String[]{"Paul", "1405"},
                new String[]{"Paul", "1630"},
                new String[]{"John", "855"},
                new String[]{"John", "915"},
                new String[]{"John", "930"},
                new String[]{"Jennifer", "1335"},
                new String[]{"Jennifer", "730"},
                new String[]{"John", "1630"}
        );

        findFrequentBadgeAccesses(badgeRecords);

    }
}
