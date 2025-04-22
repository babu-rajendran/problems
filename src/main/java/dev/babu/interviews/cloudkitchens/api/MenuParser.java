package dev.babu.interviews.cloudkitchens.api;

import dev.babu.interviews.cloudkitchens.model.MenuItem;

import java.util.List;

public interface MenuParser {

    List<MenuItem> parseMenuStream();
}
