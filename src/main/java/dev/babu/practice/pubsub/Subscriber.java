package dev.babu.practice.pubsub;

public class Subscriber implements Runnable {

    private final MessageBroker broker;
    private final String name;

    public Subscriber(String name, MessageBroker broker) {
        this.name = name;
        this.broker = broker;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message message = broker.subscribe();
                System.out.println(name + " received: " + message.getContent());
                Thread.sleep(500); // Simulate processing
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
