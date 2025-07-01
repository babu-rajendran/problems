package dev.babu.udemy.puzzles;

/**
 Given an int n, return the string form of the number followed by "!".
 So if the int is for example 13 this method should return "13!".
 However the catch is that if the number is divisible by 3 the method should return "Fizz!"
 instead of the number, and if the number is divisible by 5 it should return "Buzz!",
 and if divisible by both 3 and 5, use "FizzBuzz!". You’ll need to use the % "mod"
 for computing the remainder after division, so 23 % 10 yields 3.


 <br>
 <br>


 * <b>EXPECTATIONS:</b><br>
 fizzyWizzy(1)   <b>---></b> "1!" <br>
 fizzyWizzy(2)    <b>---></b> "2!" <br>
 fizzyWizzy(3) <b>---></b> "Fizz!" <br>
 */
public class FizzyWizzy {

    public static String fizzyWizzy(int n) {

        String result = "";

        if ((n%3 == 0) && (n%5 == 0)) {
            result = "FizzBuzz!";
        } else if (n % 3 == 0) {
            result = "Fizz!";
        } else if (n % 5 == 0) {
            result = "Buzz!";
        } else {
            result = n + "!";
        }
        return result;
    }

    public static void main(String[] args) {
        runTests();
    }

    public static void runTests() {

        int[] params1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 15, 16, 18, 19, 21, 44, 45, 100 };
        String[] expected = { "1!", "2!", "Fizz!", "4!", "Buzz!", "Fizz!", "7!", "8!", "Fizz!", "FizzBuzz!", "16!",
                "Fizz!", "19!", "Fizz!", "44!", "FizzBuzz!", "Buzz!" };


        for(int i=0; i < params1.length; i++){
            String result = FizzyWizzy.fizzyWizzy(params1[i]);
            if(result.equals(expected[i])) {
                System.out.println(printPassResult(params1[i], expected[i], result));
            } else{
                System.out.println(printFailResult(params1[i], expected[i], result));
            }
        }
    }

    private static String printPassResult(Object params1, Object expected, Object result){
        return "PASS: fizzyWizzy("+ params1.toString()+ ") -> " + result.toString();
    }


    private static String printFailResult(Object params1, Object expected, Object result){
        String ret = "**********************" + "\n";
        ret += "FAIL: fizzyWizzy("+ params1.toString()+ ") -> " + result.toString()
                + "      Expected: "+ expected.toString();
        ret += "\n" + "**********************";
        return ret;
    }
}
