package dev.babu.udemy.puzzles;

/**
 We'll say a number is cool if it's a multiple of 11 or if it is one more than a multiple of 11.
 Return true if the given non-negative number is cool. Use the % "modulus" operator
 we spoke about in the prerequisite section. Watch the lesson on modulus if you need to review
 <br>
 <br>


 * <b>EXPECTATIONS:</b><br>
 isCool(22)   <b>---></b> true <br>
 isCool(23)    <b>---></b> true <br>
 isCool(24) <b>---></b> false <br>
 */
public class CoolNumber {

    public static boolean isCool(int n) {
        return (n % 11 == 0 ) || (n % 11 == 1);
    }

    public static void main(String[] args) {
        runTests();
    }

    public static void runTests(){

        int[] params1 = { 22, 23, 24, 21, 11, 12, 10, 9, 8, 0, 1, 2, 121, 122, 123, 46, 49, 52, 54, 55 };
        boolean[] expected = { true, true, false, false, true, true, false, false, false, true, true, false, true, true,
                false, false, false, false, false, true };

        for(int i=0; i < params1.length; i++){
            boolean result = CoolNumber.isCool(params1[i]);
            if(result == expected[i]) {
                System.out.println(printPassResult(params1[i], expected[i], result));
            } else{
                System.out.println(printFailResult(params1[i], expected[i], result));
            }
        }
    }

    private static String printPassResult(Object params1, Object expected, Object result){
        return "PASS: isCool("+ params1.toString()+ ") -> " + result.toString();
    }


    private static String printFailResult(Object params1, Object expected, Object result){
        String ret = "**********************" + "\n";
        ret += "FAIL: isCool("+ params1.toString()+ ") -> " + result.toString()
                + "      Expected: "+ expected.toString();
        ret += "\n" + "**********************";
        return ret;
    }

}
