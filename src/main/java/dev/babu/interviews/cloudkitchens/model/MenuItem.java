package dev.babu.interviews.cloudkitchens.model;

import java.util.List;
import java.util.Objects;

public class MenuItem {

    private Integer id;
    private String name;
    private ItemType itemType;
    private Double price;
    private List<Integer> requiredItems;

    public MenuItem(Integer id, String name, ItemType itemType, Double price, List<Integer> requiredItems) {
        this.id = id;
        this.name = name;
        this.itemType = itemType;
        this.price = price;
        this.requiredItems = requiredItems;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Integer> getRequiredItems() {
        return requiredItems;
    }

    public void setRequiredItems(List<Integer> requiredItems) {
        this.requiredItems = requiredItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem menuItem)) return false;
        return Objects.equals(id, menuItem.id) && itemType == menuItem.itemType && Objects.equals(name, menuItem.name)
                && Objects.equals(price, menuItem.price) && Objects.equals(requiredItems, menuItem.requiredItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemType, name, price, requiredItems);
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                ", itemType=" + itemType +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", requiredItems=" + requiredItems +
                '}';
    }
}
