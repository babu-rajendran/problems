package dev.babu.karat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LineWrap {

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

    /**
     * You’re given:
     *
     * A list of lines (String[] lines) — each line is a sentence.
     *
     * An integer maxLength — the exact width every final line should have.
     *
     * You need to:
     *
     * Extract all words from the given lines.
     *
     * Wrap them into multiple new lines so that:
     *
     * Each line is no longer than maxLength.
     *
     * Words are joined using - (hyphen instead of space).
     *
     * Once wrapped, stretch each line to exactly maxLength characters by adding extra -
     * between words in a balanced way.
     *
     * You can only insert hyphens between words.
     *
     * If a line has a single word, no extra hyphen needed.
     *
     * Solution:
     * Phase 1 (Word wrapping):
     * Flatten all words from the input lines.
     *
     * Start building new lines using - as separators.
     *
     * Each time you consider adding a word:
     *
     * If current_line_length + 1 + word.length() ≤ maxLength, then add it.
     *
     * Else, push the line to the result and start a new one.
     *
     * Phase 2: (Hyphen Balancing)
     * For each line:
     *
     * If it has no hyphen, just return it as-is.
     *
     * Else, compute how many extra characters (hyphens) you need to insert.
     *
     * Distribute them round-robin style between existing hyphen positions.
     *
     */

    List<String> wrapLinesBalanced(String[] lines, int maxLength) {
        List<String> words = new ArrayList<>();
        for (String line : lines) {
            String[] tokens = line.split(" ");
            Collections.addAll(words, tokens);
        }

        List<String> unbalanced = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();
        int i = 0;

        while (i < words.size()) {
            String word = words.get(i);

            if (currentLine.isEmpty()) {
                currentLine.append(word);
                i++;
            } else if (currentLine.length() + 1 + word.length() <= maxLength) {
                currentLine.append('-').append(word);
                i++;
            } else {
                unbalanced.add(currentLine.toString());
                currentLine.setLength(0);
            }
        }
        if (!currentLine.isEmpty()) {
            unbalanced.add(currentLine.toString());
        }

        // Now balance the lines
        List<String> balanced = new ArrayList<>();
        for (String line : unbalanced) {
            if (!line.contains("-")) {
                balanced.add(line); // single word, no hyphen balancing needed
                continue;
            }

            int extraHyphens = maxLength - line.length();
            StringBuilder sb = new StringBuilder(line);
            List<Integer> hyphenPositions = new ArrayList<>();

            // Collect all hyphen positions
            for (int j = 0; j < sb.length(); j++) {
                if (sb.charAt(j) == '-') {
                    hyphenPositions.add(j);
                }
            }

            int posIndex = 0;
            while (extraHyphens > 0) {
                int insertPos = hyphenPositions.get(posIndex) + 1;
                sb.insert(insertPos, '-');

                // Update all hyphen positions after this index
                for (int k = posIndex; k < hyphenPositions.size(); k++) {
                    hyphenPositions.set(k, hyphenPositions.get(k) + 1);
                }

                extraHyphens--;
                posIndex = (posIndex + 1) % hyphenPositions.size();
            }

            balanced.add(sb.toString());
        }

        return balanced;
    }


    public static void main(String[] args) {
        LineWrap lineWrap = new LineWrap();
        String[] words = {"1p3acres", "is", "a", "good", "place", "to", "communicate"};
        int maxLength = 12;

        List<String> lines = lineWrap.wrapLines(words, maxLength);
        System.out.println(lines);

        String[] lines2 = {
                "the way it moves like me",
                "another sentence example"
        };
        int maxLength2 = 20;

        lineWrap.wrapLinesBalanced(lines2, maxLength2).forEach(System.out::println);
    }
}
