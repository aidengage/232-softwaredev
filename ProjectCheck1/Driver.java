import java.util.Scanner;

public class Driver {
    private static Location currLocation;
    public static void main(String[] args) {
        // Location kitchen = new Location("kitchen", "this is the kitchen");
        // System.out.println(kitchen.numItems());
        // Item knife = new Item("knife", "utensil", "this is a knife");
        // kitchen.addItem(knife);
        // System.out.println(kitchen.hasItem("KNIF"));
        // System.out.println(kitchen.numItems());


        currLocation = new Location("kitchen", "fred plays osu");
        currLocation.addItem(new Item("knife", "utensil", "meow"));
        currLocation.addItem(new Item("spork", "utensil", "owo"));
        currLocation.addItem(new Item("metal_straw", "utensil", "heeeeh~"));
        Scanner input = new Scanner(System.in);
        String cmd;
        String action;
        boolean isRunning = true;
        System.out.println(currLocation.getItem(1).toString());
        while (isRunning) { // we tried to use input.nextLine() and the variants, but could not get rid of the first input. so, we resorted to this and using break, which should(?) have the same outcome.
            System.out.print("enter command: ");
            cmd = input.nextLine();
            if (cmd.toLowerCase().equals("quit")) {
                input.close();
                break;
            }
            
            action = cmd.toLowerCase().split(" ")[0];
            //System.out.println(cmd);
            
            switch (action) {
                case "look":
                    System.out.println(currLocation.getName() + " -- " + currLocation.getDescription());
                    for (int i = 0; i < currLocation.numItems(); i++) {
                        System.out.println("+ " + currLocation.getItem(i).getName());
                    }
                    break;
                
                case "examine":
                    if (cmd.split(" ").length == 1) {
                        System.out.println("you must specify an item first");
                    } else {
                        String itemName = cmd.toLowerCase().split(" ")[1];
                        if (currLocation.hasItem(itemName)) {
                            System.out.println(currLocation.getItem(itemName).getName() + " 🤝 " + currLocation.getItem(itemName).getDescription());
                        } else System.out.println("Cannot find that item"); // check if user only types 'examine'
                    }
                    break;

                default:
                    System.out.println("📉 skill issue");
                    break;
            }
        }
    }
}