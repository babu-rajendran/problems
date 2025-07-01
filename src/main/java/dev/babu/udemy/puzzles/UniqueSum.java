package dev.babu.udemy.puzzles;

/**


 Given 3 int arguments - a, b, c, return their sum. However, if one of the arguments
 is the same as any of the other ones, that number should not count towards the sum.
 So basically you only sum unique numbers, not duplicates
 <br>
 <br>


 * <b>EXPECTATIONS:</b><br>
 sumUnique(1, 2, 3)   <b>---></b> 6 <br>
 sumUnique(3, 2, 3)    <b>---></b> 2 <br>
 sumUnique(3, 3, 3) <b>---></b> 0 <br>
 */
public class UniqueSum {

    public static int sumUnique(int a, int b, int c) {
        if ((a == b) && (b == c)) return 0;
        if (a == b) return c;
        if (a == c) return b;
        if (c == b) return a;

        return a + b + c;
    }

    public static void main(String[] args) {
        runTests();
    }

    public static void runTests(){



        int[] params1 = { 1, 3, 3, 9, 2, 2, 2, 4, 1 };

        int[] params2 = { 2, 2, 3, 2, 2, 9, 9, 2, 3 };


        int[] params3 = { 3, 3, 3, 2, 9, 2, 3, 3, 1 };

        int[] expected = { 6, 2, 0, 9, 9, 9, 14, 9, 3 };

        for(int i=0; i < params1.length; i++){
            int result = UniqueSum.sumUnique(params1[i], params2[i], params3[i]);
            if(result == expected[i]) {
                System.out.println(printPassResult(params1[i], params2[i],  params3[i], expected[i], result));
            } else{
                System.out.println(printFailResult(params1[i], params2[i],  params3[i], expected[i], result));
            }
        }
    }

    private static String printPassResult(Object params1, Object params2, Object params3, Object expected, Object result){
        return "PASS: sumUnique("+ params1.toString()+ ", "+ params2.toString()+ ", "+ params3.toString()+ ") -> " + result.toString();
    }


    private static String printFailResult(Object params1, Object params2, Object params3, Object expected, Object result){
        String ret = "**********************" + "\n";
        ret += "FAIL: sumUnique("+ params1.toString()+ ", "+ params2.toString()+", "+ params3.toString()+ ") -> " + result.toString()
                + "      Expected: "+ expected.toString();
        ret += "\n" + "**********************";
        return ret;
    }



}
