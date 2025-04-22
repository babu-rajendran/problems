package dev.babu.interviews.cloudkitchens.api.impl;

import dev.babu.interviews.cloudkitchens.api.MenuStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MenuStreamImpl implements MenuStream {

    private final BufferedReader reader;

    public MenuStreamImpl(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("InputStream cannot be null");
        }
        this.reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    @Override
    public String readLine() {
        try {
            return reader.readLine(); // Stream-like behavior, one line at a time
        } catch (IOException e) {
            throw new RuntimeException("Error reading from input stream", e);
        }
    }

    @Override
    public void close() {
        try {
            reader.close(); // safely closes both InputStream and Reader
        } catch (IOException e) {
            throw new RuntimeException("Error closing the input stream", e);
        }
    }
}
