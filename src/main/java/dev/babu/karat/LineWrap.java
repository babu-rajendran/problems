package dev.babu.karat;

import java.util.ArrayList;
import java.util.List;

/**
 * You're given:
 *
 * An array of words (String[] words)
 *
 * A maximum line length (int maxLength)
 *
 * You need to:
 *
 * Combine as many words as possible into a single string (a "line") such that:
 *
 * Words are separated by hyphens (-)
 *
 * The total length of the line must not exceed maxLength
 *
 * When a word cannot fit in the current line, start a new line
 */

public class LineWrap {

    List<String> wrapLines(String[] words, int maxLength) {
        List<String> result = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();
        int currentLength = 0;

        for (String word : words) {
            if (currentLine.isEmpty()) {
                // First word in the line, just add it
                currentLine.append(word);
                currentLength = word.length();
            } else {
                // Check if adding "-" and the word exceeds maxLength
                int newLength = currentLength + 1 + word.length(); // 1 for hyphen
                if (newLength <= maxLength) {
                    currentLine.append('-').append(word);
                    currentLength = newLength;
                } else {
                    // Finalize the current line and start a new one
                    result.add(currentLine.toString());
                    currentLine.setLength(0); // clear builder
                    currentLine.append(word);
                    currentLength = word.length();
                }
            }
        }

        // Add the last line if not empty
        if (!currentLine.isEmpty()) {
            result.add(currentLine.toString());
        }

        return result;
    }

    public static void main(String[] args) {
        LineWrap lineWrap = new LineWrap();
        String[] words = {"1p3acres", "is", "a", "good", "place", "to", "communicate"};
        int maxLength = 12;

        List<String> lines = lineWrap.wrapLines(words, maxLength);
        System.out.println(lines);
    }
}
