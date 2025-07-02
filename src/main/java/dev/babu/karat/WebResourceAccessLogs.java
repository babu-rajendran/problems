package dev.babu.karat;

import java.util.*;

/**
 * Suppose we have an unsorted log file of accesses to web resources. Each log entry consists of an access time, the
 * ID of the user making the access, and the resource ID. The access time is represented as seconds since 00:00:00, and
 * all times are assumed to be in the same day. For example:
 *
 * logs1 = [
 * ["58523", "user_1", "resource_1"],
 * ["62314", "user_2", "resource_2"],
 * ["54001", "user_1", "resource_3"],
 * ["200", "user_6", "resource_5"],
 * ["215", "user_6", "resource_4"],
 * ["54060", "user_2", "resource_3"],
 * ["53760", "user_3", "resource_3"],
 * ["58522", "user_22", "resource_1"],
 * ["53651", "user_5", "resource_3"],
 * ["2", "user_6", "resource_1"],
 * ["100", "user_6", "resource_6"],
 * ["400", "user_7", "resource_2"],
 * ["100", "user_8", "resource_6"],
 * ["54359", "user_1", "resource_3"],
 * ]
 *
 */

public class WebResourceAccessLogs {

    static class LogEntry {
        int time;
        String user;
        String resource;

        LogEntry(int time, String user, String resource) {
            this.time = time;
            this.user = user;
            this.resource = resource;
        }
    }


    /**
     *  * Write a function that takes the logs and returns each users min and max access timestamp.
     *  * Example Output:
     *  *
     *  * user_3:[53760,53760]
     *  * user_2:[54060,62314]
     *  * user_1:[54001,58523]
     *  * user_7:[400,400]
     *  * user_6:[2,215]
     *  * user_5:[53651,53651]
     *  * user_4:[58522,58522]
     *  * user_8:[100,100]
     *
     * @param logs
     * @return
     */
    Map<String, int[]> findMinAndMaxAccessTime(String[][] logs) {
        Map<String, int[]> userAccessTimes = new HashMap<>();

        for (String[] log : logs) {
            int time = Integer.parseInt(log[0]);
            String user = log[1];

            if (!userAccessTimes.containsKey(user)) {
                userAccessTimes.put(user, new int[]{time, time});
            } else {
                int[] times = userAccessTimes.get(user);
                times[0] = Math.min(times[0], time); // min time
                times[1] = Math.max(times[1], time); // max time
            }
        }

        return userAccessTimes;
    }

    /**
     * Write a function that takes the logs and returns the resource with the highest number of
     * accesses in any 5 minute window, together with how many accesses it saw.
     *
     * Expected Output:
     * most_requested_resource(logs1)  => ('resource_3', 3)
     *
     * @param logs
     * @return
     */
    String mostRequestedResource(String[][] logs) {
        Map<String, List<Integer>> resourceAccesses = new HashMap<>();

        // Group timestamps by resource
        for (String[] log : logs) {
            int time = Integer.parseInt(log[0]);
            String resource = log[2];

            resourceAccesses
                    .computeIfAbsent(resource, k -> new ArrayList<>())
                    .add(time);
        }

        String maxResource = null;
        int maxCount = 0;

        for (Map.Entry<String, List<Integer>> entry : resourceAccesses.entrySet()) {
            String resource = entry.getKey();
            List<Integer> times = entry.getValue();
            Collections.sort(times);

            int left = 0;

            // Sliding window
            for (int right = 0; right < times.size(); right++) {
                while (times.get(right) - times.get(left) > 300) {
                    left++;
                }

                int count = right - left + 1;
                if (count > maxCount) {
                    maxCount = count;
                    maxResource = resource;
                }
            }
        }

        return "(" + maxResource + ", " + maxCount + ")";
    }

    /**
     * Write a function that takes the logs as input, builds the transition graph and returns it as
     * an adjacency list with probabilities. Add START and END states.
     * Specifically, for each resource, we want to compute a list of every possible next step
     * taken by any user, together with the corresponding probabilities. The list of resources
     * should include START but not END, since by definition END is a terminal state.
     *
     * Expected output for logs1:
     *
     * transition_graph(logs1) =>
     * {{
     * 'START': {'resource_1': 0.25, 'resource_2': 0.125, 'resource_3': 0.5, 'resource_6': 0.125},
     * 'resource_1': {'resource_6': 0.33, 'END': 0.667},
     * 'resource_2': {'END': 1.0},
     * 'resource_3': {'END': 0.4, 'resource_1': 0.2, 'resource_2': 0.2, 'resource_3': 0.2},
     * 'resource_4': {'END': 1.0},
     * 'resource_5': {'resource_4': 1.0},
     * 'resource_6': {'END': 0.5, 'resource_5': 0.5}
     * }}
     * @param logs
     * @return
     */
    Map<String, Map<String, Double>> buildTransitionGraph(String[][] logs) {
        // 1. Group logs by user
        Map<String, List<LogEntry>> userSessions = new HashMap<>();
        for (String[] log : logs) {
            int time = Integer.parseInt(log[0]);
            String user = log[1];
            String resource = log[2];
            userSessions.computeIfAbsent(user, k -> new ArrayList<>())
                    .add(new LogEntry(time, user, resource));
        }

        // 2. Build transitions: Map<From, Map<To, Count>>
        Map<String, Map<String, Integer>> transitions = new HashMap<>();

        for (List<LogEntry> session : userSessions.values()) {
            session.sort(Comparator.comparingInt(e -> e.time));
            List<String> path = new ArrayList<>();
            path.add("START");
            for (LogEntry entry : session) {
                path.add(entry.resource);
            }
            path.add("END");

            for (int i = 0; i < path.size() - 1; i++) {
                String from = path.get(i);
                String to = path.get(i + 1);
                transitions.computeIfAbsent(from, k -> new HashMap<>());
                Map<String, Integer> destMap = transitions.get(from);
                destMap.put(to, destMap.getOrDefault(to, 0) + 1);
            }
        }

        // 3. Convert counts to probabilities
        Map<String, Map<String, Double>> graph = new HashMap<>();

        for (Map.Entry<String, Map<String, Integer>> entry : transitions.entrySet()) {
            String from = entry.getKey();
            Map<String, Integer> destCounts = entry.getValue();

            int total = destCounts.values().stream().mapToInt(i -> i).sum();
            Map<String, Double> probMap = new HashMap<>();

            for (Map.Entry<String, Integer> destEntry : destCounts.entrySet()) {
                String to = destEntry.getKey();
                int count = destEntry.getValue();
                probMap.put(to, Math.round((count * 1000.0) / total) / 1000.0); // rounded to 3 decimals
            }
            graph.put(from, probMap);
        }

        return graph;
    }

    static Map<String, Map<String, Double>> sortGraph(Map<String, Map<String, Double>> graph) {
        Map<String, Map<String, Double>> sorted = new LinkedHashMap<>();

        // Add START first
        if (graph.containsKey("START")) {
            sorted.put("START", sortInnerMap(graph.get("START")));
        }

        // Collect and sort other keys (excluding START)
        List<String> keys = new ArrayList<>(graph.keySet());
        keys.remove("START");
        Collections.sort(keys); // lexicographical order: resource_1, resource_2, etc.

        for (String key : keys) {
            sorted.put(key, sortInnerMap(graph.get(key)));
        }

        return sorted;
    }

    static Map<String, Double> sortInnerMap(Map<String, Double> inner) {
        Map<String, Double> sorted = new LinkedHashMap<>();

        // Collect and sort keys excluding "END"
        inner.keySet().stream()
                .filter(k -> !k.equals("END"))
                .sorted()
                .forEach(k -> sorted.put(k, inner.get(k)));

        // Put "END" last if it exists
        if (inner.containsKey("END")) {
            sorted.put("END", inner.get("END"));
        }

        return sorted;
    }

    static void printGraph(Map<String, Map<String, Double>> graph) {
        for (String from : graph.keySet()) {
            System.out.print(from + ": {");
            Map<String, Double> dests = graph.get(from);
            List<String> parts = new ArrayList<>();
            for (String to : dests.keySet()) {
                parts.add(to + ": " + dests.get(to));
            }
            System.out.println(String.join(", ", parts) + "}");
        }
    }

    public static void main(String[] args) {
        WebResourceAccessLogs webResourceAccessLogs = new WebResourceAccessLogs();

        String[][] logs = {
                {"58523", "user_1", "resource_1"},
                {"62314", "user_2", "resource_2"},
                {"54001", "user_1", "resource_3"},
                {"200", "user_6", "resource_5"},
                {"215", "user_6", "resource_4"},
                {"54060", "user_2", "resource_3"},
                {"53760", "user_3", "resource_3"},
                {"58522", "user_22", "resource_1"},
                {"53651", "user_5", "resource_3"},
                {"2", "user_6", "resource_1"},
                {"100", "user_6", "resource_6"},
                {"400", "user_7", "resource_2"},
                {"100", "user_8", "resource_6"},
                {"54359", "user_1", "resource_3"},
        };

        webResourceAccessLogs.findMinAndMaxAccessTime(logs).entrySet().stream().sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.println(e.getKey() + " -> " + Arrays.toString(e.getValue())));
        System.out.println("Most requested resource: " + webResourceAccessLogs.mostRequestedResource(logs));

        Map<String, Map<String, Double>> graph = webResourceAccessLogs.buildTransitionGraph(logs);
        Map<String, Map<String, Double>> sortedGraph = sortGraph(graph);
        printGraph(sortedGraph);
    }
}
