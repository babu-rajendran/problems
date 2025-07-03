package dev.babu.karat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The people who buy ads on our network don't have enough data about how ads are working for their business. They've
 * asked us to find out which ads produce the most purchases on their website. Our client provided us with a list of
 * user IDs of customers who bought something on a landing page after clicking one of their ads:
 *
 * Each user completed 1 purchase.
 * completed_purchase_user_ids = [
 * "3123122444","234111110", "8321125440", "99911063"]
 * And our ops team provided us with some raw log data from our ad server showing every time a user clicked on one of
 * our ads:
 *
 * ad_clicks = [
 * #"IP_Address,Time,Ad_Text",
 * "122.121.0.1,2016-11-03 11:41:19,Buy wool coats for your pets",
 * "96.3.199.11,2016-10-15 20:18:31,2017 Pet Mittens",
 * "122.121.0.250,2016-11-01 06:13:13,The Best Hollywood Coats",
 * "82.1.106.8,2016-11-12 23:05:14,Buy wool coats for your pets",
 * "92.130.6.144,2017-01-01 03:18:55,Buy wool coats for your pets",
 * "92.130.6.145,2017-01-01 03:18:55,2017 Pet Mittens",
 * ]
 *
 * The client also sent over the IP addresses of all their users.
 *
 * all_user_ips = [
 * #"User_ID,IP_Address",
 * "2339985511,122.121.0.155",
 * "234111110,122.121.0.1",
 * "3123122444,92.130.6.145",
 * "39471289472,2001:0db8:ac10:fe01:0000:0000:0000:0000",
 * "8321125440,82.1.106.8",
 * "99911063,92.130.6.144"
 * ]
 *
 * Write a function to parse this data, determine how many times each ad was clicked, then return the ad text, that
 * ad's number of clicks, and how many of those ad clicks were from users who made a purchase.
 *
 * Expected Output:
 *
 * Bought Clicked Ad Text
 * 1 of 2  2017 Pet Mittens
 * 0 of 1  The Best Hollywood Coats
 * 3 of 3  Buy wool coats for your pets
 */

public class AdsConversionRate {

    public static void main(String[] args) {
        List<String> completedPurchaseUserIds = Arrays.asList(
                "3123122444", "234111110", "8321125440", "99911063"
        );

        List<String> adClicks = Arrays.asList(
                "122.121.0.1,2016-11-03 11:41:19,Buy wool coats for your pets",
                "96.3.199.11,2016-10-15 20:18:31,2017 Pet Mittens",
                "122.121.0.250,2016-11-01 06:13:13,The Best Hollywood Coats",
                "82.1.106.8,2016-11-12 23:05:14,Buy wool coats for your pets",
                "92.130.6.144,2017-01-01 03:18:55,Buy wool coats for your pets",
                "92.130.6.145,2017-01-01 03:18:55,2017 Pet Mittens"
        );

        List<String> allUserIps = Arrays.asList(
                "2339985511,122.121.0.155",
                "234111110,122.121.0.1",
                "3123122444,92.130.6.145",
                "39471289472,2001:0db8:ac10:fe01:0000:0000:0000:0000",
                "8321125440,82.1.106.8",
                "99911063,92.130.6.144"
        );

        analyzeAdClicks(completedPurchaseUserIds, adClicks, allUserIps);
    }

    private static void analyzeAdClicks(List<String> purchaseUserIds, List<String> adClicks, List<String> userIps) {
        // Step 1: Map IP → User ID
        Map<String, String> ipToUserId = new HashMap<>();
        for (String entry : userIps) {
            String[] parts = entry.split(",");
            ipToUserId.put(parts[1], parts[0]);
        }

        // Step 2: Build Set of Purchase User IDs
        Set<String> purchaseSet = new HashSet<>(purchaseUserIds);

        // Step 3: Map Ad Text → [clickCount, purchaseCount]
        Map<String, int[]> adStats = new LinkedHashMap<>();

        for (String click : adClicks) {
            String[] parts = click.split(",");
            String ip = parts[0];
            String adText = parts[2];

            int[] stats = adStats.getOrDefault(adText, new int[2]); // [clicks, purchases]
            stats[0]++; // total clicks

            String userId = ipToUserId.get(ip);
            if (userId != null && purchaseSet.contains(userId)) {
                stats[1]++; // purchases
            }

            adStats.put(adText, stats);
        }

        // Step 4: Print Results
        System.out.println("Bought Clicked Ad Text");
        for (Map.Entry<String, int[]> entry : adStats.entrySet()) {
            int[] stat = entry.getValue();
            System.out.println(stat[1] + " of " + stat[0] + "  " + entry.getKey());
        }
    }
}
