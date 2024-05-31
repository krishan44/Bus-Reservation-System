import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BusReservationSystem {
    private static final int MAX_CUSTOMERS = 100; // Maximum number of customers
    private static final int MAX_BUSES = 50; // Maximum number of buses
    private static Customer[] customers = new Customer[MAX_CUSTOMERS]; // Array to store customers
    private static Bus[] buses = new Bus[MAX_BUSES]; // Array to store buses
    private static int numCustomers = 0; // Variable to track the number of registered customers
    private static int numBuses = 0; // Variable to track the number of registered buses

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("1. Register Customer");
            System.out.println("2. Register Bus");
            System.out.println("3. Search Buses");
            System.out.println("4. Reserve Seat");
            System.out.println("5. Cancel Reservation");
            System.out.println("6. Request New Seat");
            System.out.println("7. Display All Reservations");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    registerCustomer(scanner);
                    break;
                case 2:
                    registerBus(scanner);
                    break;
                case 3:
                    searchBuses(scanner);
                    break;
                case 4:
                    reserveSeat(scanner);
                    break;
                case 5:
                    cancelReservation(scanner);
                    break;
                case 6:
                    requestNewSeat(scanner);
                    break;
                case 7:
                    displayAllReservations();
                    break;
                case 8:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void registerCustomer(Scanner scanner) {
        if (numCustomers == MAX_CUSTOMERS) {
            System.out.println("Maximum number of customers reached.");
            return;
        }

        scanner.nextLine(); // Consume newline
        System.out.println("Enter customer details:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Mobile number: ");
        String mobileNumber = scanner.nextLine();
        System.out.print("Email ID: ");
        String email = scanner.nextLine();
        System.out.print("City: ");
        String city = scanner.nextLine();
        System.out.print("Age: ");
        int age = scanner.nextInt();

        Customer customer = new Customer(name, mobileNumber, email, city, age);
        customers[numCustomers++] = customer;

        System.out.println("Customer registered successfully!");
    }

    private static void registerBus(Scanner scanner) {
        if (numBuses == MAX_BUSES) {
            System.out.println("Maximum number of buses reached.");
            return;
        }

        scanner.nextLine(); // Consume newline
        System.out.println("Enter bus details:");
        System.out.print("Bus number: ");
        String busNumber = scanner.nextLine();
        System.out.print("Total seats: ");
        int totalSeats = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Starting point: ");
        String startingPoint = scanner.nextLine();
        System.out.print("Ending point: ");
        String endingPoint = scanner.nextLine();
        System.out.print("Starting time: ");
        String startingTime = scanner.nextLine();
        System.out.print("Fare: ");
        double fare = scanner.nextDouble();

        Bus bus = new Bus(busNumber, totalSeats, startingPoint, endingPoint, startingTime, fare);
        buses[numBuses++] = bus;

        System.out.println("Bus registered successfully!");
    }

    private static void searchBuses(Scanner scanner) {
        scanner.nextLine(); // Consume newline
        System.out.println("Enter search criteria:");
        System.out.print("Starting point: ");
        String startingPoint = scanner.nextLine();
        System.out.print("Ending point: ");
        String endingPoint = scanner.nextLine();

        boolean found = false;
        for (int i = 0; i < numBuses; i++) {
            Bus bus = buses[i];
            if (bus.getStartingPoint().equalsIgnoreCase(startingPoint) && 
                bus.getEndingPoint().equalsIgnoreCase(endingPoint)) {
                System.out.println("Bus number: " + bus.getBusNumber());
                System.out.println("Total seats: " + bus.getTotalSeats());
                System.out.println("Starting time: " + bus.getStartingTime());
                System.out.println("Fare: " + bus.getFare());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No buses found for the given criteria.");
        }
    }

    private static void reserveSeat(Scanner scanner) {
        // Prompt the user to select a bus
        System.out.println("Select a bus to reserve a seat:");
        displayAllBuses();

        System.out.print("Enter the bus number: ");
        String busNumber = scanner.next();

        // Find the selected bus
        Bus selectedBus = null;
        for (int i = 0; i < numBuses; i++) {
            if (buses[i].getBusNumber().equalsIgnoreCase(busNumber)) {
                selectedBus = buses[i];
                break;
            }
        }

        if (selectedBus == null) {
            System.out.println("Bus not found. Please try again.");
            return;
        }

        // Display available seats on the selected bus
        System.out.println("Available seats on Bus " + selectedBus.getBusNumber() + ":");
        for (int i = 0; i < selectedBus.getTotalSeats(); i++) {
            if (!selectedBus.isSeatReserved(i)) {
                System.out.print((i + 1) + " ");
            }
        }
        System.out.println();

        // Prompt the user to select a seat
        System.out.print("Enter the seat number to reserve: ");
        int seatNumber = scanner.nextInt();

        // Check if the selected seat is available
        if (seatNumber < 1 || seatNumber > selectedBus.getTotalSeats()) {
            System.out.println("Invalid seat number. Please try again.");
            return;
        }

        if (selectedBus.isSeatReserved(seatNumber - 1)) {
            System.out.println("Seat " + seatNumber + " is already reserved. Please try again.");
            return;
        }

        // Reserve the seat
        selectedBus.reserveSeat(seatNumber - 1);
        System.out.println("Seat " + seatNumber + " reserved successfully.");
    }

    private static void cancelReservation(Scanner scanner) {
        // Prompt the user to select a bus
        System.out.println("Select a bus to cancel reservation:");
        displayAllBuses();

        System.out.print("Enter the bus number: ");
        String busNumber = scanner.next();

        // Find the selected bus
        Bus selectedBus = null;
        for (int i = 0; i < numBuses; i++) {
            if (buses[i].getBusNumber().equalsIgnoreCase(busNumber)) {
                selectedBus = buses[i];
                break;
            }
        }

        if (selectedBus == null) {
            System.out.println("Bus not found. Please try again.");
            return;
        }

        // Prompt the user to select a seat to cancel reservation
        System.out.println("Enter the seat number to cancel reservation:");
        for (int i = 0; i < selectedBus.getTotalSeats(); i++) {
            if (selectedBus.isSeatReserved(i)) {
                System.out.print((i + 1) + " ");
            }
        }
        System.out.println();

        int seatNumber = scanner.nextInt();

        // Check if the selected seat is reserved
        if (seatNumber < 1 || seatNumber > selectedBus.getTotalSeats()) {
            System.out.println("Invalid seat number. Please try again.");
            return;
        }

        if (!selectedBus.isSeatReserved(seatNumber - 1)) {
            System.out.println("Seat " + seatNumber + " is not reserved. Please try again.");
            return;
        }

        // Cancel the reservation
        selectedBus.cancelReservation(seatNumber - 1);
        System.out.println("Reservation for seat " + seatNumber + " canceled successfully.");
    }

    private static void requestNewSeat(Scanner scanner) {
        // Prompt the user to select a bus
        System.out.println("Select a bus to request a new seat:");
        displayAllBuses();

        System.out.print("Enter the bus number: ");
        String busNumber = scanner.next();

        // Find the selected bus
        Bus selectedBus = null;
        for (int i = 0; i < numBuses; i++) {
            if (buses[i].getBusNumber().equalsIgnoreCase(busNumber)) {
                selectedBus = buses[i];
                break;
            }
        }

        if (selectedBus == null) {
            System.out.println("Bus not found. Please try again.");
            return;
        }

        // Display available seats on the selected bus
        System.out.println("Available seats on Bus " + selectedBus.getBusNumber() + ":");
        for (int i = 0; i < selectedBus.getTotalSeats(); i++) {
            if (!selectedBus.isSeatReserved(i)) {
                System.out.print((i + 1) + " ");
            }
        }
        System.out.println();

        // Prompt the user to select a new seat
        System.out.print("Enter the new seat number: ");
        int newSeatNumber = scanner.nextInt();

        // Check if the selected seat is available
        if (newSeatNumber < 1 || newSeatNumber > selectedBus.getTotalSeats()) {
            System.out.println("Invalid seat number. Please try again.");
            return;
        }

        if (selectedBus.isSeatReserved(newSeatNumber - 1)) {
            System.out.println("Seat " + newSeatNumber + " is already reserved. Please try again.");
            return;
        }

        // Reserve the new seat
        selectedBus.reserveSeat(newSeatNumber - 1);
        System.out.println("New seat " + newSeatNumber + " reserved successfully.");
    }

    private static void displayAllReservations() {
        if (numBuses == 0) {
            System.out.println("No buses registered yet.");
            return;
        }

        System.out.println("All Reservations:");
        for (int i = 0; i < numBuses; i++) {
            Bus bus = buses[i];
            System.out.println("Bus Number: " + bus.getBusNumber());
            System.out.println("Starting Point: " + bus.getStartingPoint());
            System.out.println("Ending Point: " + bus.getEndingPoint());
            System.out.println("Starting Time: " + bus.getStartingTime());
            System.out.println("Fare: " + bus.getFare());
            System.out.println("Reserved Seats:");
            for (int j = 0; j < bus.getTotalSeats(); j++) {
                if (bus.isSeatReserved(j)) {
                    System.out.println("Seat " + (j + 1) + ": " + bus.getReservedCustomerName(j) + " (" + bus.getReservedCustomerMobileNumber(j) + ")");
                }
            }
            System.out.println();
        }
    }


    private static void displayAllBuses() {
        System.out.println("Available Buses:");
        for (int i = 0; i < numBuses; i++) {
            System.out.println((i + 1) + ". " + buses[i].getBusNumber());
        }
    }
}

class Customer {
    private String name;
    private String mobileNumber;
    private String email;
    private String city;
    private int age;

    public Customer(String name, String mobileNumber, String email, String city, int age) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.city = city;
        this.age = age;
    }

    // Getters and setters
}

class Bus {
    private String busNumber;
    private int totalSeats;
    private String startingPoint;
    private String endingPoint;
    private String startingTime;
    private double fare;
    private boolean[] seats;
    private Queue<Integer> availableSeats; // Queue to store available seat numbers

    public Bus(String busNumber, int totalSeats, String startingPoint, String endingPoint, String startingTime, double fare) {
        this.busNumber = busNumber;
        this.totalSeats = totalSeats;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.startingTime = startingTime;
        this.fare = fare;
        this.seats = new boolean[totalSeats]; // Initialize seats array
        this.availableSeats = new LinkedList<>(); // Initialize queue for available seats
        for (int i = 0; i < totalSeats; i++) {
            availableSeats.offer(i); // Add all seats to the queue initially
        }
    }

    public String getBusNumber() {
        return busNumber;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public String getEndingPoint() {
        return endingPoint;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public double getFare() {
        return fare;
    }

    public boolean isSeatReserved(int seatNumber) {
        return seats[seatNumber];
    }

    public void reserveSeat(int seatNumber) {
        seats[seatNumber] = true;
        availableSeats.remove(seatNumber); // Remove the reserved seat from the available seats queue
    }

    public void cancelReservation(int seatNumber) {
        seats[seatNumber] = false; // Mark the seat as available
        availableSeats.offer(seatNumber); // Add the canceled seat back to the available seats queue
    }

    public String getReservedCustomerName(int seatNumber) {
        // Placeholder method for retrieving the name of the customer who reserved the seat
        return "Customer Name";
    }

    public String getReservedCustomerMobileNumber(int seatNumber) {
        // Placeholder method for retrieving the mobile number of the customer who reserved the seat
        return "Customer Mobile Number";
    }
}
