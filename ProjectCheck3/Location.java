import java.util.ArrayList;
import java.util.HashMap;

public class Location {
    private String name;
    private String description;
    private ArrayList<Item> items;

    //cp2
    private HashMap<String, Location> connections;

    public Location(String name, String description) {
        this.name = name;
        this.description = description;
        items = new ArrayList<Item>();

        //cp2
        connections = new HashMap<String, Location>();
    }

    //cp2
    public void connect(String direction, Location place) {
        connections.put(direction.toLowerCase(), place);
    }

    //cp2
    public boolean canMove(String direction) {
        if (connections.get(direction.toLowerCase()) != null) {
            return true;
        }
        return false;
    }

    //cp2
    public Location getLocation(String direction) { //check if returns null when not connected, probably doesnt :)
        return connections.get(direction.toLowerCase());
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
        Item temp;
        for (int i = 0; i < items.size(); i++) {
            temp = items.get(i);
            if (items.get(i).getName().compareToIgnoreCase(name) == 0) {
                items.remove(i);
                return temp;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "\u001B[1m" + getName() + "\u001B[0m" + " " + getDescription();
    }
}
