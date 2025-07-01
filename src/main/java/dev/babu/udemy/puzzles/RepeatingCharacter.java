package dev.babu.udemy.puzzles;

/**
 Given a string, return a string where for every char in the original, append another.
 <br>
 <br>

 * <b>EXPECTATIONS:</b><br>
 repeatChar("The")  <b>---></b>"TThhee"<br>
 repeatChar("AAbb")    <b>---></b> "AAAAbbbb" <br>
 repeatChar("Hi-There") <b>---></b> "HHii--TThheerree" <br>
 */

public class RepeatingCharacter {

    public static String repeatChar(String str) {
        return str;
    }

    public static void main(String[] args) {
        runTests();
    }

    public static void runTests(){

        String[] params1 = { "The", "AAbb", "Hi-There", "Word!", "!!", "", "a", ".", "aa" };

        String[] expected = { "TThhee", "AAAAbbbb", "HHii--TThheerree", "WWoorrdd!!", "!!!!", "", "aa", "..", "aaaa" };

        for(int i=0; i < params1.length; i++){
            String result = RepeatingCharacter.repeatChar(params1[i]);
            if(result.equals(expected[i])) {
                System.out.println(printPassResult(params1[i], expected[i], result));
            } else{
                System.out.println(printFailResult(params1[i], expected[i], result));
            }
        }
    }

    private static String printPassResult(Object params1, Object expected, Object result){
        return "PASS: repeatChar("+ params1.toString()+ ") -> " + result.toString();
    }


    private static String printFailResult(Object params1, Object expected, Object result){
        String ret = "**********************" + "\n";
        ret += "FAIL: repeatChar("+ params1.toString()+") -> " + result.toString()
                + "      Expected: "+ expected.toString();
        ret += "\n" + "**********************";
        return ret;
    }
}
