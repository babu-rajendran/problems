package dev.babu.interviews.cloudkitchens.api.impl;

import dev.babu.interviews.cloudkitchens.api.MenuParser;
import dev.babu.interviews.cloudkitchens.api.MenuStream;
import dev.babu.interviews.cloudkitchens.model.ItemType;
import dev.babu.interviews.cloudkitchens.model.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MenuParserImpl implements MenuParser {

    private static final Logger LOG = LoggerFactory.getLogger(MenuParser.class);

    @Override
    public List<MenuItem> parseMenuStream() {
        List<MenuItem> menuItems = new ArrayList<>();
        List<String> buffer = new ArrayList<>();
        try (MenuStream menuStream = new MenuStreamImpl(
                getClass().getClassLoader().getResourceAsStream("menu.txt"))) {


            String line;
            while ((line = menuStream.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    if (!buffer.isEmpty()) {
                        menuItems.add(parseMenuItem(buffer));
                        buffer.clear();
                    }
                } else {
                    buffer.add(line);
                }
            }
            // Handle the last item if there is no trailing blank line
            if (!buffer.isEmpty()) {
                menuItems.add(parseMenuItem(buffer));
            }
        } catch (Exception e) {
            LOG.error("Failed to parse menu stream", e);
        }
        return menuItems;
    }

    private MenuItem parseMenuItem(List<String> lines) {
        int id = Integer.parseInt(lines.get(0));
        String name = lines.get(1);
        String category = lines.get(2);
        Double price = null;
        List<Integer> requiredItemIds = new ArrayList<>();

        int index = 3;

        // If the category is not Ingredient, then price should be present
        if (!category.equalsIgnoreCase("Ingredient") && index < lines.size()) {
            try {
                price = Double.parseDouble(lines.get(index));
                index++;
            } catch (NumberFormatException e) {
                // No price present or price line missing; leave price as null
            }
        }

        // Remaining lines are IDs of required items
        for (int i = index; i < lines.size(); i++) {
            requiredItemIds.add(Integer.parseInt(lines.get(i)));
        }

        return new MenuItem(id, name, ItemType.valueOf(category.toUpperCase()), price, requiredItemIds);
    }
}
