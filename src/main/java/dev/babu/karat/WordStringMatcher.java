package dev.babu.karat;

import java.util.*;

/**
 * Given a string and an array of words, find the first word that can be created by using letters of
 * the string. For eg. if the string is "balloons" and words are ["son", "ball", "friends"] return "son" as
 * it is the first word that can be constructed using letters from "balloons".
 */

public class WordStringMatcher {
    public static void main(String[] args) {
        String input = "baloons";
        String[] words = {"ball", "nose", "son"};
        String output = findFirstWord(input, words);
        System.out.println(output);
    }

    static String findFirstWord(String input, String[] words) {
        Map<Character, Integer> frequencyMap = new HashMap<>();

        for (char c : input.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        for (String word : words) {
            if (canConstructWord(word, frequencyMap)) {
                return word;
            }
        }

        return null;
    }

    static boolean canConstructWord(String word, Map<Character, Integer> charCount) {
        // Create a copy of charCount to not modify the original
        Map<Character, Integer> tempCount = new HashMap<>(charCount);

        for (char ch : word.toCharArray()) {
            if (!tempCount.containsKey(ch) || tempCount.get(ch) == 0) {
                return false; // If letter is missing or not enough occurrences
            }
            tempCount.put(ch, tempCount.get(ch) - 1);
        }

        return true;
    }
}
