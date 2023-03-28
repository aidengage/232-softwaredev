//cp2
import java.util.ArrayList;
import java.lang.String;

public class ContainerItem extends Item {
    private ArrayList<Item> items;

    public ContainerItem(String containerName, String containerType, String description) {
        super(containerName, containerType, description);
        items = new ArrayList<Item>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public boolean hasItem(String name) {
        String item;
        for (int i = 0; i < items.size(); i++) {
            item = items.get(i).getName();
            if (item.compareToIgnoreCase(name) == 0) {
                return true;
            }
        }
        return false;
    }

    public Item getItem(String name) {
        Item temp;
        for(int i = 0; i < items.size(); i++) {
            temp = items.get(i);
            if (temp.getName().compareToIgnoreCase(name) == 0) {
                return temp;
            }
        }
        return null;
    }

    public Item removeItem(String name) {
        Item temp;
        for (int i = 0; i < items.size(); i++) {
            temp = items.get(i);
            if (temp.getName().compareToIgnoreCase(name) == 0) {
                items.remove(i);
                return temp;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String allItems = new String();
        for (int i = 0; i < items.size(); i++) {
            allItems = allItems.concat("\u001B[1m\n+ " + items.get(i).getName() + "\u001B[0m");
        }
        return (super.toString() + allItems);
    }
}
