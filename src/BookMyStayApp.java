import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    String reservationId;
    String roomType;

    public Reservation(String id, String type) {
        this.reservationId = id;
        this.roomType = type;
    }
}

class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

class PersistenceService {

    private static final String FILE_NAME = "hotel_state.dat";

    public void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("State saved successfully.");

        } catch (Exception e) {
            System.out.println("Error saving state.");
        }
    }

    public SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            return (SystemState) ois.readObject();

        } catch (Exception e) {
            System.out.println("No previous state found. Starting fresh.");
            return null;
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PersistenceService persistence = new PersistenceService();

        Map<String, Integer> inventory = new HashMap<>();
        List<Reservation> bookings = new ArrayList<>();

        SystemState loadedState = persistence.load();

        if (loadedState != null) {
            inventory = loadedState.inventory;
            bookings = loadedState.bookings;
            System.out.println("State restored successfully.");
        } else {
            inventory.put("Standard", 2);
            inventory.put("Deluxe", 2);
        }

        int choice;

        do {
            System.out.println("\n===== BookMyStay Menu =====");
            System.out.println("1. Book Room");
            System.out.println("2. View Bookings");
            System.out.println("3. View Inventory");
            System.out.println("4. Save & Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Reservation ID: ");
                    String id = sc.nextLine();

                    System.out.print("Enter Room Type: ");
                    String type = sc.nextLine();

                    int available = inventory.getOrDefault(type, 0);

                    if (available > 0) {
                        inventory.put(type, available - 1);
                        bookings.add(new Reservation(id, type));
                        System.out.println("Booking successful!");
                    } else {
                        System.out.println("No rooms available.");
                    }
                    break;

                case 2:
                    System.out.println("\nBookings:");
                    for (Reservation r : bookings) {
                        System.out.println(r.reservationId + " | " + r.roomType);
                    }
                    break;

                case 3:
                    System.out.println("\nInventory:");
                    for (Map.Entry<String, Integer> e : inventory.entrySet()) {
                        System.out.println(e.getKey() + " : " + e.getValue());
                    }
                    break;

                case 4:
                    SystemState state = new SystemState(inventory, bookings);
                    persistence.save(state);
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 4);

        sc.close();
    }
}