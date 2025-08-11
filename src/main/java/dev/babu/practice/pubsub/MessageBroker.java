package dev.babu.practice.pubsub;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageBroker {
    private final BlockingQueue<Message> queue;

    public MessageBroker() {
        this.queue = new LinkedBlockingQueue<>();
    }

    public void publish(Message message) throws InterruptedException {
        queue.put(message); // Adds message, blocks if queue is full
        System.out.println("Published: " + message.getContent());
    }

    public Message subscribe() throws InterruptedException {
        Message message = queue.take(); // Retrieves message, blocks if queue is empty
        return message;
    }
}
