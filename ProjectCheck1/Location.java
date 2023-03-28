import java.util.ArrayList;

public class Location {
    private String name;
    private String description;
    private ArrayList<Item> items;

    public Location(String name, String description) {
        this.name = name;
        this.description = description;
        items = new ArrayList<Item>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public boolean hasItem(String name) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().compareToIgnoreCase(name) == 0) {
                return true;
            }
        }
        return false;
    }

    public Item getItem(String name) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().compareToIgnoreCase(name) == 0) {
                return items.get(i);
            }
        }
        return null;
    }

    public Item getItem(int index) {
        if (index >= items.size()) {
            return null;
        } else {
            return items.get(index);
        }
    }

    public int numItems() {
        return items.size();
    }

    public Item removeItem(String name) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().compareToIgnoreCase(name) == 0) {
                return items.remove(i);
            }
        }
        return null;
    }
}
