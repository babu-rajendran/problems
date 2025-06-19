package dev.babu.karat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given a string and an array of words, find the first word that can be created by
 * using letters of the string. For eg. if the string is "balloons" and words
 * are ["son", "ball", "friends"] return "son" as
 * it is the first word that can be constructed using letters from "balloons".
 */

public class WordStringMatcher {

    Map<Character, Integer> frequencyMap(String word) {
        Map<Character, Integer> frequency = new HashMap<>();

        for (char c : word.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }

        return frequency;
    }

    boolean canConstruct(String word, Map<Character, Integer> sourceMap) {
        Map<Character, Integer> wordMap = new HashMap<>();

        for (char c : word.toCharArray()) {
            wordMap.put(c, wordMap.getOrDefault(c, 0) + 1);
        }

        for (Map.Entry<Character, Integer> entry : wordMap.entrySet()) {
            char c = entry.getKey();
            int count = entry.getValue();

            if (sourceMap.getOrDefault(c, 0) < count) {
                return false;
            }
        }
        return true;

    }

    String findFirstWord(String s, List<String> words) {
        Map<Character, Integer> frequency = frequencyMap(s);

        for (String word : words) {
            if (canConstruct(word, frequency)) {
                return word;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        WordStringMatcher wordStringMatcher = new WordStringMatcher();
        List<String> words = List.of("ball", "friends", "son");
        String word = wordStringMatcher.findFirstWord("balloons", words);
        System.out.println("First Word --> " + word);
    }
}
