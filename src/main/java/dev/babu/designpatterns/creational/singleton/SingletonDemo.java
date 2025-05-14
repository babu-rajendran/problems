package dev.babu.designpatterns.creational.singleton;

public class SingletonDemo {
    
    public static void main(String[] args) {
        Logger logger1 = Logger.getInstance();
        logger1.log("First log message");

        Logger logger2 = Logger.getInstance();
        logger2.log("Second log message");

        // Verifying if both logger1 and logger2 are the same instance
        if (logger1 == logger2) {
            System.out.println("Both logger instances are the same (Singleton works!)");
        } else {
            System.out.println("Different instances (Singleton failed)");
        }
    }
}
