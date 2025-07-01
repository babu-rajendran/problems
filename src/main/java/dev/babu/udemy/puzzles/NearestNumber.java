package dev.babu.udemy.puzzles;

/**


 Given 2 positive int arguments (a, b), return whichever argument is
 nearest to the number 21 without going over.
 Return 0 if they both go over 21.
 <br>
 <br>


 * <b>EXPECTATIONS:</b><br>
 nearestTwentyOne(19, 21)   <b>---></b> 21 <br>
 nearestTwentyOne(21, 19)    <b>---></b> 21 <br>
 nearestTwentyOne(19, 22) <b>---></b> 19 <br>
 nearestTwentyOne(32, 22) <b>---></b> 0 <br>
 */

public class NearestNumber {

    public static int nearestTwentyOne(int a, int b) {

        if (a > 21 && b > 21) return 0;

        if (a > 21) return b;
        if (b > 21) return a;

        if (a > b)
            return a;
        else
            return b;
    }

    public static void main(String[] args) {
        runTests();
    }

    public static void runTests(){



        int[] params1 = { 19, 21, 19, 22, 22, 22, 33, 1, 34, 17, 18, 16, 3, 3, 21 };


        int[] params2 = { 21, 19, 22, 19, 50, 22, 1, 2, 33, 19, 17, 23, 4, 2, 20 };


        int[] expected = { 21, 21, 19, 19, 0, 0, 1, 2, 0, 19, 18, 16, 4, 3, 21 };

        for(int i=0; i < params1.length; i++){
            int result = NearestNumber.nearestTwentyOne(params1[i], params2[i]);
            if(result == expected[i]) {
                System.out.println(printPassResult(params1[i], params2[i], expected[i], result));
            } else{
                System.out.println(printFailResult(params1[i], params2[i], expected[i], result));
            }
        }
    }

    private static String printPassResult(Object params1, Object params2, Object expected, Object result){
        return "PASS: nearestTwentyOne("+ params1.toString()+ ", "+ params2.toString()+ ") -> " + result.toString();
    }


    private static String printFailResult(Object params1, Object params2, Object expected, Object result){
        String ret = "**********************" + "\n";
        ret += "FAIL: nearestTwentyOne("+ params1.toString()+ ", "+ params2.toString()+ ") -> " + result.toString()
                + "      Expected: "+ expected.toString();
        ret += "\n" + "**********************";
        return ret;
    }


}
