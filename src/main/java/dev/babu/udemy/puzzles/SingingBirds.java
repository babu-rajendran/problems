package dev.babu.udemy.puzzles;

/**
 The birds in Florida like to sing during favorable temperatures.
 In particular, they sing if the temperature is between 60 and 90 (inclusive).
 Unless it is summer, then the upper limit is 100 instead of 90.
 Given an int temperature and a boolean isSummer,
 return true if the birds are singing and false otherwise. <br>
 <br>


 * <b>EXPECTATIONS:</b><br>
 birdsSinging(70, false)   <b>---></b> true <br>
 birdsSinging(95, false)    <b>---></b> false <br>
 birdsSinging(95, true) <b>---></b> true <br>
 */
public class SingingBirds {

    public static void main(String[] args) {
        runTests();
    }

    public static boolean birdsSinging(int temp, boolean isSummer) {

        int lowerLimit = 60;
        int upperLimit = 90;

        if (isSummer) {
            upperLimit = 100;
        }

        return (temp >= lowerLimit) && (temp <= upperLimit);
    }



    public static void runTests(){



        int[] params1 = { 70, 95, 95, 90, 90, 50, 50, 100, 100, 105, 59, 59, 60 };


        boolean[] params2 = { false, false, true, false, true, false, true, false, true, true, false, true, false };


        boolean[] expected = { true, false, true, true, true, false, false, false, true, false, false, false, true };

        for(int i=0; i < params1.length; i++){
            boolean result = SingingBirds.birdsSinging(params1[i], params2[i]);
            if(result == expected[i]) {
                System.out.println(printPassResult(params1[i], params2[i], expected[i], result));
            } else{
                System.out.println(printFailResult(params1[i], params2[i], expected[i], result));
            }
        }
    }

    private static String printPassResult(Object params1, Object params2, Object expected, Object result){
        return "PASS: birdsSinging("+ params1.toString()+ ", "+ params2.toString()+ ") -> " + result.toString();
    }


    private static String printFailResult(Object params1, Object params2, Object expected, Object result){
        String ret = "**********************" + "\n";
        ret += "FAIL: birdsSinging("+ params1.toString()+ ", "+ params2.toString()+ ") -> " + result.toString()
                + "      Expected: "+ expected.toString();
        ret += "\n" + "**********************";
        return ret;
    }


}
