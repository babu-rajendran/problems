package dev.babu.interviews.cloudkitchens.api;

public interface MenuStream extends AutoCloseable {

    String readLine();

    @Override
    void close();
}
