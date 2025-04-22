package dev.babu.interviews.cloudkitchens;

import dev.babu.interviews.cloudkitchens.api.MenuParser;
import dev.babu.interviews.cloudkitchens.api.impl.MenuParserImpl;

public class App {

    public static void main(String[] args) {
        MenuParser menuParser = new MenuParserImpl();
        menuParser.parseMenuStream().forEach(System.out::println);
    }
}
