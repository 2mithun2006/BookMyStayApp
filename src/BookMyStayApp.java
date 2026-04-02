import java.util.*;

/**
 * Reservation class
 * Represents a confirmed booking
 */
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

/**
 * Add-On Service class
 */
class AddOnService {
    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }
}

/**
 * Add-On Service Manager
 * Handles mapping between reservation and services
 */
class AddOnServiceManager {

    // One-to-Many Mapping
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    // Get services for reservation
    public List<AddOnService> getServices(String reservationId) {
        return serviceMap.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {
        double total = 0;
        List<AddOnService> services = serviceMap.get(reservationId);

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getPrice();
            }
        }
        return total;
    }
}

/**
 * Main Application
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AddOnServiceManager manager = new AddOnServiceManager();

        // Simulated existing reservation (core booking unchanged)
        Reservation reservation = new Reservation("RES101", "Mithun", "Deluxe");

        int choice;

        do {
            System.out.println("\n===== Add-On Service Menu =====");
            System.out.println("1. Add Service");
            System.out.println("2. View Services");
            System.out.println("3. Calculate Total Cost");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Service Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Price: ");
                    double price = sc.nextDouble();

                    AddOnService service = new AddOnService(name, price);
                    manager.addService(reservation.getReservationId(), service);

                    System.out.println("Service added successfully!");
                    break;

                case 2:
                    List<AddOnService> services =
                            manager.getServices(reservation.getReservationId());

                    if (services.isEmpty()) {
                        System.out.println("No services added.");
                    } else {
                        System.out.println("Services for Reservation " +
                                reservation.getReservationId() + ":");

                        for (AddOnService s : services) {
                            System.out.println("- " + s.getServiceName()
                                    + " : ₹" + s.getPrice());
                        }
                    }
                    break;

                case 3:
                    double total = manager.calculateTotalCost(
                            reservation.getReservationId());

                    System.out.println("Total Add-On Cost: ₹" + total);
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