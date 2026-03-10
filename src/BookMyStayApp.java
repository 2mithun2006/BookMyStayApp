import java.util.*;

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        int current = inventory.get(roomType);
        inventory.put(roomType, current - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

class BookingService {

    private Queue<Reservation> requestQueue;
    private RoomInventory inventory;
    private Set<String> allocatedRoomIds = new HashSet<>();
    private Map<String, Set<String>> allocatedRoomsByType = new HashMap<>();

    public BookingService(Queue<Reservation> requestQueue, RoomInventory inventory) {
        this.requestQueue = requestQueue;
        this.inventory = inventory;
    }

    public void processBookings() {

        while (!requestQueue.isEmpty()) {

            Reservation reservation = requestQueue.poll();
            String roomType = reservation.getRoomType();

            System.out.println("\nProcessing booking for " + reservation.getGuestName());

            if (inventory.getAvailability(roomType) > 0) {

                String roomId = generateRoomId(roomType);

                allocatedRoomIds.add(roomId);

                allocatedRoomsByType
                        .computeIfAbsent(roomType, k -> new HashSet<>())
                        .add(roomId);

                inventory.decrementRoom(roomType);

                System.out.println("Reservation Confirmed!");
                System.out.println("Guest: " + reservation.getGuestName());
                System.out.println("Room Type: " + roomType);
                System.out.println("Assigned Room ID: " + roomId);

            } else {
                System.out.println("Reservation Failed. No available rooms for type: " + roomType);
            }
        }
    }

    private String generateRoomId(String roomType) {

        String roomId;

        do {
            roomId = roomType.substring(0, 2).toUpperCase() + "-" + (int)(Math.random() * 1000);
        } while (allocatedRoomIds.contains(roomId));

        return roomId;
    }

    public void displayAllocations() {
        System.out.println("\nAllocated Rooms:");

        for (Map.Entry<String, Set<String>> entry : allocatedRoomsByType.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Standard", 2);
        inventory.addRoomType("Deluxe", 1);

        Queue<Reservation> requestQueue = new LinkedList<>();

        requestQueue.add(new Reservation("Alice", "Standard"));
        requestQueue.add(new Reservation("Bob", "Deluxe"));
        requestQueue.add(new Reservation("Charlie", "Standard"));
        requestQueue.add(new Reservation("David", "Deluxe"));

        BookingService bookingService = new BookingService(requestQueue, inventory);

        bookingService.processBookings();
        bookingService.displayAllocations();
        inventory.displayInventory();
    }
}
