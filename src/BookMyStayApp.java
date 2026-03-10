
abstract class Room {

    protected String roomType;
    protected int beds;
    protected int size;
    protected double price;

    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Room Size: " + size + " sq.ft");
        System.out.println("Price per Night: $" + price);
    }
}

/**
 * SingleRoom Class
 * Represents a single occupancy room.
 * @version 2.0
 */
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 200, 100);
    }
}

/**
 * DoubleRoom Class
 * Represents a double occupancy room.
 * @version 2.0
 */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 350, 180);
    }
}

/**
 * SuiteRoom Class
 * Represents a luxury suite room.
 * @version 2.0
 */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 500, 300);
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay App           ");
        System.out.println("   Hotel Booking System v2.1     ");
        System.out.println("=================================\n");

        // Create room objects
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static availability variables
        int singleAvailability = 5;
        int doubleAvailability = 3;
        int suiteAvailability = 2;

        // Display Single Room
        System.out.println("---- Single Room Details ----");
        singleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + singleAvailability + "\n");

        // Display Double Room
        System.out.println("---- Double Room Details ----");
        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + doubleAvailability + "\n");

        // Display Suite Room
        System.out.println("---- Suite Room Details ----");
        suiteRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + suiteAvailability + "\n");

        System.out.println("Application execution completed.");
    }
}