import java.util.Scanner;
import java.util.HashMap;

public class Driver {
    private static Location currLocation;
    private static ContainerItem myInventory;
    private static HashMap<String, String> commands = new HashMap<String, String>();

    private static void createWorld() {
        Location kitchen = new Location("Kitchen", "this do be a kitchen with shit in it");
        Location hallway = new Location("Hallway", "walk through here to go places");
        Location bathroom = new Location("Bathroom", "you shit here :)");
        Location livingRoom = new Location("LivingRoom", "people live here but dont sleep");
        Location bedroom = new Location("Bedroom", "people sleep here");

        kitchen.connect("north", hallway);
        kitchen.connect("west", livingRoom);
        hallway.connect("south", kitchen);
        hallway.connect("east", bathroom);
        bathroom.connect("west", hallway);
        livingRoom.connect("east", kitchen);
        hallway.connect("west", bedroom);
        bedroom.connect("east", hallway);

        kitchen.addItem(new Item("knife", "utensil", "cut things"));
        kitchen.addItem(new Item("plate", "dishes", "eat on this"));
        hallway.addItem(new Item("lamp", "furniture", "bring light into the world"));
        bathroom.addItem(new Item("toiletpaper", "commodity", "a hot commodity a couple years ago"));
        livingRoom.addItem(new Item("tv", "furniture", "watch the world happen through this"));
        livingRoom.addItem(new Item("couch", "furniture", "sit here and be comfy"));
        bedroom.addItem(new Item("bed", "furniture", "this is where you sleep"));
        
        currLocation = kitchen;

        myInventory = new ContainerItem("player", "inventory", "this is the player's inventory");

        ContainerItem chest = new ContainerItem("chest", "storage", "stores items in the room");
        ContainerItem desk = new ContainerItem("desk", "storage", "used for item storage and work");
        ContainerItem vault = new ContainerItem("vault", "secure storage", "mmmmmm money :)");

        chest.addItem(new Item("blanket", "accessory", "used for warmth"));
        chest.addItem(new Item("jacket", "clothes", "wear jacket when cold"));
        desk.addItem(new Item("pen", "writing", "used to write and sign documents"));
        desk.addItem(new Item("paper", "writing", "used to write on and store information"));
        desk.addItem(new Item("stapler", "stationary", "used to attach papers together"));
        vault.addItem(new Item("money", "currency", "a wad of cash, likely one thousand dollars"));

        livingRoom.addItem(chest);
        bedroom.addItem(desk);
        bedroom.addItem(vault);
    }

    public static void addHelp(String command, String description) {
        commands.put(command, description);
    }

    public static void displayHelp() {
        for (String key : commands.keySet()) {
            System.out.println("\u001B[1m" + key + "\u001B[0m" + ": " + commands.get(key));
        }
    }
    
    public static void main(String[] args) {

        createWorld();

        addHelp("look", "checks the area for items");
        addHelp("examine", "gives information on an item of choice");
        addHelp("go", "moves player in a direction of choice");
        addHelp("inventory", "shows what is in the player's inventory");
        addHelp("take", "put an item of choice from the area into the player's inventory, can also take an item from a container");
        addHelp("drop", "takes an item of choice of of the player's inventory and leaves it in the area");
        addHelp("help", "displays all the commands availible to the user");
        addHelp("Put", "this command takes an item from your inventory and puts it in a container in the room");

        Scanner input = new Scanner(System.in);
        String cmd;
        String action;
        boolean isRunning = true;
        int length;
        String itemName;
        String containerName;
        ContainerItem temp;
        String[] words;

        while (isRunning) {
            System.out.print("enter command: ");
            cmd = input.nextLine();

            if (cmd.toLowerCase().equals("quit")) {
                input.close();
                break;
            }
            
            words = cmd.toLowerCase().split(" ");
            action = words[0];
            length = words.length;

            switch (action) {
                case "look":
                    System.out.println("\u001B[1m" + currLocation.getName() + "\u001B[m -- " + currLocation.getDescription());
                    for (int i = 0; i < currLocation.numItems(); i++) {
                        System.out.println("\u001B[1m+ " + currLocation.getItem(i).getName() + "\u001B[0m");
                    }
                break;
                
                case "examine":
                    if (length == 1) {
                        System.out.println("you must specify an item first");
                    } else {
                        itemName = words[1];
                        if (currLocation.hasItem(itemName)) {
                            if (currLocation.getItem(itemName) instanceof ContainerItem) {
                                System.out.println(currLocation.getItem(itemName));
                            } else {
                                System.out.println("\u001B[1m" + currLocation.getItem(itemName).getName() + "\u001B[0m" + " -- " + currLocation.getItem(itemName).getDescription());
                            }
                        } else System.out.println("Cannot find that item");
                    }
                break;

                //cp2
                case "go":
                    if (length == 1) {
                        System.out.println("you must specify a direction");
                    } else {
                        String direction = words[1];
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
                    if (length == 1) {
                        System.out.println("you must specify an item to take");
                    } else if (length <= 4) {
                        itemName = words[1];
                        if (length == 4) {
                            containerName = words[3]; //optimize this stuff
                            if (currLocation.getItem(containerName) instanceof ContainerItem) {
                                temp = (ContainerItem)currLocation.getItem(containerName);
                                if (temp.getItem(itemName) instanceof Item) {
                                    myInventory.addItem(temp.removeItem(itemName));
                                } else {
                                    System.out.println(itemName + " is not in " + containerName);
                                }
                            } else {
                                System.out.println(containerName + " does not exist there");
                            }
                        } else if (currLocation.hasItem(itemName)) {
                            if (currLocation.getItem(itemName) instanceof ContainerItem) {
                                System.out.println("you need an extra person to lift that");
                            } else {
                                myInventory.addItem(currLocation.removeItem(itemName));
                            }
                        } else {
                            System.out.println(itemName + " must exist at your location");
                        }
                    } else {
                        System.out.println("???");
                    }

                break;

                //cp2
                case "drop":
                    if (length == 1) {
                        System.out.println("you must specify an item to drop");
                    } else {
                        itemName = words[1];
                        if (myInventory.hasItem(itemName)) {
                            currLocation.addItem(myInventory.removeItem(itemName));
                        } else {
                            System.out.println("the item must exist in your inventory");
                        }
                        
                        
                    }
                break;

                case "put":
                    if (length == 4) {
                        itemName = words[1];
                        containerName = words[3];
                        if (currLocation.getItem(containerName) instanceof ContainerItem) {
                            temp = (ContainerItem)currLocation.getItem(containerName);
                            if (myInventory.getItem(itemName) instanceof Item) {
                                temp.addItem(myInventory.removeItem(itemName));
                            } else {
                                System.out.println(itemName + " is not in your inventory");
                            }
                        } else {
                            System.out.println(containerName + " does not exist there");
                        }
                    } else {
                        System.out.println("what item do you want to put in where?");
                    }
                break;

                //cp2
                case "help":
                    displayHelp();
                break;

                default:
                    System.out.println("please enter a command");
                break;
            }
        }
    }
}