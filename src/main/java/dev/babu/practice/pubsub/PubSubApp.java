package dev.babu.practice.pubsub;

public class PubSubApp {

    public static void main(String[] args) {
        MessageBroker broker = new MessageBroker();

        // Create and start publisher threads
        Thread publisher1 = new Thread(new Publisher("Publisher-A", broker));
        Thread publisher2 = new Thread(new Publisher("Publisher-B", broker));
        publisher1.start();
        publisher2.start();

        // Create and start subscriber threads
        Thread subscriber1 = new Thread(new Subscriber("Subscriber-X", broker));
        Thread subscriber2 = new Thread(new Subscriber("Subscriber-Y", broker));
        subscriber1.start();
        subscriber2.start();
    }

}
