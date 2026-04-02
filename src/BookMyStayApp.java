import java.util.*;

class Reservation {
    private String reservationId;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }
}

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    public boolean isAvailable(String type) {
        return inventory.getOrDefault(type, 0) > 0;
    }

    public void allocateRoom(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public void releaseRoom(String type) {
        inventory.put(type, inventory.get(type) + 1);
    }

    public void displayInventory() {
        System.out.println("\nInventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

class BookingService {
    private Map<String, Reservation> confirmedBookings = new HashMap<>();
    private Map<String, Integer> roomCounter = new HashMap<>();

    public Reservation confirmBooking(String reservationId, String roomType, RoomInventory inventory) {

        if (!inventory.isAvailable(roomType)) {
            System.out.println("No rooms available.");
            return null;
        }

        int count = roomCounter.getOrDefault(roomType, 0) + 1;
        roomCounter.put(roomType, count);

        String roomId = roomType.substring(0, 1).toUpperCase() + count;

        inventory.allocateRoom(roomType);

        Reservation reservation = new Reservation(reservationId, roomType, roomId);
        confirmedBookings.put(reservationId, reservation);

        return reservation;
    }

    public Reservation getReservation(String reservationId) {
        return confirmedBookings.get(reservationId);
    }

    public void removeReservation(String reservationId) {
        confirmedBookings.remove(reservationId);
    }

    public void displayBookings() {
        System.out.println("\nConfirmed Bookings:");
        for (Reservation r : confirmedBookings.values()) {
            System.out.println(r.getReservationId() + " | " + r.getRoomType() + " | " + r.getRoomId());
        }
    }
}

class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String reservationId, BookingService bookingService, RoomInventory inventory) {

        Reservation reservation = bookingService.getReservation(reservationId);

        if (reservation == null) {
            System.out.println("Cancellation failed: Reservation not found.");
            return;
        }

        rollbackStack.push(reservation.getRoomId());

        inventory.releaseRoom(reservation.getRoomType());

        bookingService.removeReservation(reservationId);

        System.out.println("Booking cancelled successfully. Released Room ID: " + rollbackStack.pop());
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService();
        CancellationService cancellationService = new CancellationService();

        int choice;

        do {
            System.out.println("\n===== BookMyStay Menu =====");
            System.out.println("1. Confirm Booking");
            System.out.println("2. Cancel Booking");
            System.out.println("3. View Bookings");
            System.out.println("4. View Inventory");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Reservation ID: ");
                    String id = sc.nextLine();

                    System.out.print("Enter Room Type: ");
                    String type = sc.nextLine();

                    Reservation r = bookingService.confirmBooking(id, type, inventory);

                    if (r != null) {
                        System.out.println("Booking Confirmed: " + r.getRoomId());
                    }
                    break;

                case 2:
                    System.out.print("Enter Reservation ID to cancel: ");
                    String cancelId = sc.nextLine();

                    cancellationService.cancelBooking(cancelId, bookingService, inventory);
                    break;

                case 3:
                    bookingService.displayBookings();
                    break;

                case 4:
                    inventory.displayInventory();
                    break;

                case 5:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);

        sc.close();
    }
}