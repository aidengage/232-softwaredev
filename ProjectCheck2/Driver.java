import java.util.Scanner;

//cp2
import java.util.HashMap;

public class Driver {
    private static Location currLocation;

    //cp2
    private static ContainerItem myInventory;
    private static HashMap<String, String> commands = new HashMap<String, String>();

    //cp2
    private static void createWorld() {
        Location kitchen = new Location("Kitchen", "this do be a kitchen with shit in it");
        Location hallway = new Location("Hallway", "walk through here to go places");
        Location bathroom = new Location("Bathroom", "you shit here :)");
        Location livingRoom = new Location("LivingRoom", "people live here but dont sleep");

        kitchen.connect("north", hallway);
        kitchen.connect("west", livingRoom);
        hallway.connect("south", kitchen);
        hallway.connect("east", bathroom);
        bathroom.connect("west", hallway);
        livingRoom.connect("east", kitchen);

        kitchen.addItem(new Item("knife", "utensil", "cut things"));
        kitchen.addItem(new Item("plate", "dishes", "eat on this"));
        hallway.addItem(new Item("lamp", "furniture", "bring light into the world"));
        bathroom.addItem(new Item("toiletpaper", "commodity", "a hot commodity a couple years ago"));
        livingRoom.addItem(new Item("tv", "furniture", "watch the world happen through this"));
        livingRoom.addItem(new Item("couch", "furniture", "sit here and be comfy"));
        currLocation = kitchen;
        myInventory = new ContainerItem("player", "inventory", "this is the player's inventory");
    }

    
    //cp2
    public static void addHelp(String command, String description) {
        commands.put(command, description);
    }

    //cp2
    public static void displayHelp() {
        for (String key : commands.keySet()) {
            System.out.println(key + ": " + commands.get(key));
        }
    }
    

    public static void main(String[] args) {

        //cp2
        createWorld();

        //help commands cp2
        addHelp("look", "checks the area for items");
        addHelp("examine", "gives information on an item of choice");
        addHelp("go", "moves player in a direction of choice");
        addHelp("inventory", "shows what is in the player's inventory");
        addHelp("take", "put an item of choice from the area into the player's inventory");
        addHelp("drop", "takes an item of choice of of the player's inventory and leaves it in the area");
        addHelp("help", "displays all the commands availible to the user");

        Scanner input = new Scanner(System.in);
        String cmd;
        String action;
        boolean isRunning = true;
        
        // Location kitchen = new Location("kitchen", "this is the kitchen");
        // System.out.println(kitchen.numItems());
        // Item knife = new Item("knife", "utensil", "this is a knife");
        // kitchen.addItem(knife);
        // System.out.println(kitchen.hasItem("KNIF"));
        // System.out.println(kitchen.numItems());

        /*
        currLocation = new Location("kitchen", "fred plays osu");
        currLocation.addItem(new Item("knife", "utensil", "meow"));
        currLocation.addItem(new Item("spork", "utensil", "owo"));
        currLocation.addItem(new Item("metal_straw", "utensil", "heeeeh~"));
        */
        
        //System.out.println(currLocation.getItem(1).toString());
    

        //cp2
        // Location hallway = new Location("Hallway", "this is a hallway");
        // Location bathroom = new Location("Bathroom", "this is the bathroom");
        // hallway.connect("north", bathroom);
        // bathroom.connect("South", hallway);
        // System.out.println(hallway.canMove("North"));
        // System.out.println(bathroom.canMove("south"));
        // System.out.println((hallway.getLocation("north").toString()));
        // System.out.println(hallway.getLocation("south"));

        //program starts
        while (isRunning) { // we tried to use input.nextLine() and the variants, but could not get rid of the first input. so, we resorted to this and using break, which should(?) have the same outcome.
            System.out.print("enter command: ");
            cmd = input.nextLine();
            if (cmd.toLowerCase().equals("quit")) {
                input.close();
                break;
            }
            
            action = cmd.toLowerCase().split(" ")[0];
            
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

                //cp2
                case "go":
                    if (cmd.split(" ").length == 1) {
                        System.out.println("you must specify a direction");
                    } else {
                        String direction = cmd.toLowerCase().split(" ")[1];
                        if (direction.equals("north") || direction.equals("south") || direction.equals("west") || direction.equals("east")) { //try to figure this out with enum Direction
                            if (currLocation.canMove(direction)) {
                                currLocation = currLocation.getLocation(direction);
                            } else {
                                System.out.println("there must be a room there");
                            }
                        } else {
                            System.out.println("must be north, south, east, or west");
                        }
                    }
                    break;
                
                //cp2
                case "inventory": //should just call toString() from ContainerItem class
                    if (myInventory == null) {
                        System.out.println("you have no items, you are itemless, zero items");
                    } else {
                    System.out.println(myInventory);
                    }
                break;

                //cp2
                case "take":
                    if (cmd.split(" ").length == 1) {
                        System.out.println("you must specify an item to take");
                    } else {
                        String itemName = cmd.toLowerCase().split(" ")[1];
                        if (currLocation.hasItem(itemName)) {
                            myInventory.addItem(currLocation.removeItem(itemName));
                        } else {
                            System.out.println("the item must exist at your location");
                        }
                    }
                    break;

                //cp2
                case "drop":
                    if (cmd.split(" ").length == 1) {
                        System.out.println("you must specify an item to drop");
                    } else {
                        String itemName = cmd.toLowerCase().split(" ")[1];
                        if (myInventory.hasItem(itemName)) {
                            currLocation.addItem(myInventory.removeItem(itemName));
                        } else {
                            System.out.println("the item must exist in your inventory");
                        }
                        
                        
                    }
                    break;

                //cp2
                case "help":
                    displayHelp();
                break;

                default:
                    System.out.println("📉 skill issue");
                    break;
            }
        }
    }
}