import java.util.*;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return history;
    }
}

class BookingReportService {

    public void displayAllBookings(List<Reservation> reservations) {

        if (reservations.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        System.out.println("\n=== Booking History ===");

        for (Reservation r : reservations) {
            System.out.println("ID: " + r.getReservationId()
                    + " | Guest: " + r.getGuestName()
                    + " | Room: " + r.getRoomType());
        }
    }

    public void generateSummaryReport(List<Reservation> reservations) {

        Map<String, Integer> summary = new HashMap<>();

        for (Reservation r : reservations) {
            String type = r.getRoomType();
            summary.put(type, summary.getOrDefault(type, 0) + 1);
        }

        System.out.println("\n=== Booking Summary Report ===");

        for (Map.Entry<String, Integer> entry : summary.entrySet()) {
            System.out.println(entry.getKey() + " Rooms Booked: " + entry.getValue());
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        int choice;

        do {
            System.out.println("\n===== BookMyStay Menu =====");
            System.out.println("1. Confirm Booking");
            System.out.println("2. View Booking History");
            System.out.println("3. Generate Summary Report");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Reservation ID: ");
                    String id = sc.nextLine();

                    System.out.print("Enter Guest Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Room Type: ");
                    String room = sc.nextLine();

                    Reservation reservation = new Reservation(id, name, room);

                    history.addReservation(reservation);

                    System.out.println("Booking confirmed and added to history!");
                    break;

                case 2:
                    reportService.displayAllBookings(history.getAllReservations());
                    break;

                case 3:
                    reportService.generateSummaryReport(history.getAllReservations());
                    break;

                case 4:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 4);

        sc.close();
    }
}