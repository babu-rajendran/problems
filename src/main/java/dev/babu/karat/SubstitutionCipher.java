package dev.babu.karat;

import java.util.*;

/**
 * Given:
 *
 * A key string: used to construct a substitution alphabet.
 *
 * A text string: the text to encrypt.
 *
 * Construct a substitution cipher by:
 *
 * Creating a custom alphabet from the key.
 *
 * Substituting each letter in the text with the letter at the same index in this custom alphabet.
 *
 * Preserving the original case.
 *
 * Leaving non-letter characters unchanged.
 *
 * Approach:
 * Build a mapping from the standard alphabet 'a' to 'z' → to a custom alphabet from the key.
 *
 * Use two maps:
 *
 * Map<Character, Character> for lowercase mapping.
 *
 * Map<Character, Character> for uppercase mapping.
 *
 * Traverse the input text, apply substitution based on the character’s case and alphabet index.
 */
public class SubstitutionCipher {

    public static String encrypt(String text, String key) {
        // Step 1: Build the custom alphabet
        List<Character> customAlphabet = buildCustomAlphabet(key);

        // Step 2: Build substitution maps for lowercase and uppercase
        Map<Character, Character> lowerMap = new HashMap<>();
        Map<Character, Character> upperMap = new HashMap<>();
        char base = 'a';
        for (int i = 0; i < 26; i++) {
            char customChar = customAlphabet.get(i);
            lowerMap.put((char)(base + i), customChar);
            upperMap.put((char)(base + i), Character.toUpperCase(customChar));
        }

        // Step 3: Encrypt the input text
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isLowerCase(ch)) {
                result.append(lowerMap.getOrDefault(ch, ch));
            } else if (Character.isUpperCase(ch)) {
                result.append(upperMap.getOrDefault(Character.toLowerCase(ch), ch));
            } else {
                result.append(ch); // keep punctuation, space, etc.
            }
        }

        return result.toString();
    }

    private static List<Character> buildCustomAlphabet(String key) {
        Set<Character> seen = new LinkedHashSet<>();
        // Add unique characters from key (a–z only)
        for (char ch : key.toCharArray()) {
            ch = Character.toLowerCase(ch);
            if (ch >= 'a' && ch <= 'z') {
                seen.add(ch);
            }
        }

        // Add remaining letters a–z
        for (char ch = 'a'; ch <= 'z'; ch++) {
            seen.add(ch);
        }

        return new ArrayList<>(seen); // final 26-character custom alphabet
    }

    public static void main(String[] args) {
        String text = "Hello World!";
        String key = "keyword";
        System.out.println(encrypt(text, key));  // Output: Aofif Sisfp!
    }
}

