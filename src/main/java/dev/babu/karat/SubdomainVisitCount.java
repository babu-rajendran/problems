package dev.babu.karat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubdomainVisitCount {

    /**
     * A website domain "discuss.leetcode.com" consists of various subdomains. At the top level, we have "com", at the
     * next level, we have "leetcode.com" and at the lowest level, "discuss.leetcode.com". When we visit a domain like
     * "discuss.leetcode.com", we will also visit the parent domains "leetcode.com" and "com" implicitly.
     *
     * A count-paired domain is a domain that has one of the two formats "rep d1.d2.d3" or "rep d1.d2" where rep is the
     * number of visits to the domain and d1.d2.d3 is the domain itself.
     *
     * For example, "9001 discuss.leetcode.com" is a count-paired domain that indicates that discuss.leetcode.com was
     * visited 9001 times.
     * Given an array of count-paired domains cpdomains, return an array of the count-paired domains of each subdomain
     * in the input. You may return the answer in any order.
     *
     */
    public static List<String> subdomainVisits(String[] cpdomains) {
        Map<String, Integer> countMap = new HashMap<>();

        for (String cpdomain : cpdomains) {
            String[] parts = cpdomain.split(" ");
            int count = Integer.parseInt(parts[0]);
            String domain = parts[1];

            String[] fragments = domain.split("\\.");
            StringBuilder subdomain = new StringBuilder();

            for (int i = fragments.length - 1; i >= 0; i--) {
                if (subdomain.length() > 0) {
                    subdomain.insert(0, ".");
                }
                subdomain.insert(0, fragments[i]);
                countMap.put(subdomain.toString(), countMap.getOrDefault(subdomain.toString(), 0) + count);
            }
        }

        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            result.add(entry.getValue() + " " + entry.getKey());
        }

        return result;
    }

    public static void main(String[] args) {
        String[] cpdomains = new String[] {"900 google.mail.com", "50 yahoo.com", "1 intel.mail.com", "5 wiki.org"};
        subdomainVisits(cpdomains).forEach(System.out::println);
    }

}
