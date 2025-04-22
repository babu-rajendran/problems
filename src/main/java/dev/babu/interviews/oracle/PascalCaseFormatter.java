package dev.babu.interviews.oracle;

public class PascalCaseFormatter {

    public String formatToPascalCase(String input) {
        int n = input.length();
        StringBuilder prefix = new StringBuilder();
        StringBuilder suffix = new StringBuilder();
        StringBuilder result = new StringBuilder();

        int i = 0;

        // 1. Count and store leading underscores
        while (i < n && input.charAt(i) == '_') {
            prefix.append('_');
            i++;
        }

        // 2. Count trailing underscores
        int j = n - 1;
        while (j >= 0 && input.charAt(j) == '_') {
            suffix.append('_');
            j--;
        }

        // 3. Extract the core substring without prefix/suffix underscores
        String core = input.substring(i, j + 1); // may be empty

        // 4. Split the core by underscores and convert non-empty parts to PascalCase
        StringBuilder middle = new StringBuilder();
        String[] parts = core.split("_+"); // split by one or more underscores

        for (String part : parts) {
            if (!part.isEmpty()) {
                middle.append(Character.toUpperCase(part.charAt(0)));
                if (part.length() > 1) {
                    middle.append(part.substring(1));
                }
            }
        }

        // 5. Build the final result
        result.append(prefix).append(middle).append(suffix);

        return result.toString();
    }

    public static void main(String[] args) {
        PascalCaseFormatter formatter = new PascalCaseFormatter();
        String input = "__hello___world__!__";
        String output = formatter.formatToPascalCase(input);
        System.out.println(output); // Expected: __HelloWorld!___
    }

}
