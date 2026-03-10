import java.util.HashMap;

class Room {
    String type;
    int beds;
    int size;
    double price;

    Room(String type, int beds, int size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    void displayDetails(int availability) {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Room Size: " + size + " sq.ft");
        System.out.println("Price per Night: $" + price);
        System.out.println("Available Rooms: " + availability);
        System.out.println();
    }
}

class RoomInventory {

    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0);
    }

    int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay App           ");
        System.out.println("   Hotel Booking System v4.0     ");
        System.out.println("=================================\n");

        RoomInventory inventory = new RoomInventory();

        Room single = new Room("Single Room", 1, 200, 100);
        Room doubleRoom = new Room("Double Room", 2, 350, 180);
        Room suite = new Room("Suite Room", 3, 500, 300);

        System.out.println("Available Rooms:\n");

        int singleAvail = inventory.getAvailability("Single Room");
        if (singleAvail > 0) {
            single.displayDetails(singleAvail);
        }

        int doubleAvail = inventory.getAvailability("Double Room");
        if (doubleAvail > 0) {
            doubleRoom.displayDetails(doubleAvail);
        }

        int suiteAvail = inventory.getAvailability("Suite Room");
        if (suiteAvail > 0) {
            suite.displayDetails(suiteAvail);
        }

        System.out.println("Search completed.");
    }
}