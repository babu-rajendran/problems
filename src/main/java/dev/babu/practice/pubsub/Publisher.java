package dev.babu.practice.pubsub;

public class Publisher implements Runnable {

    private final MessageBroker broker;
    private final String name;

    public Publisher(String name, MessageBroker broker) {
        this.name = name;
        this.broker = broker;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                Message message = new Message("Message " + i + " from " + name);
                broker.publish(message);
                Thread.sleep(1000); // Simulate work
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
