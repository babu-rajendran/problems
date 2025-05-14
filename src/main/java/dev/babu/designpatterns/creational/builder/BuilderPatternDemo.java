package dev.babu.designpatterns.creational.builder;

public class BuilderPatternDemo {

    public static void main(String[] args) {
        User user1 = new User.UserBuilder("Babu", "babu@email.com")
                .setPhone("123-456-7890")
                .setAddress("San Francisco")
                .build();

        User user2 = new User.UserBuilder("Raj", "raj@email.com").build();

        System.out.println(user1);
        System.out.println(user2);
    }

}
