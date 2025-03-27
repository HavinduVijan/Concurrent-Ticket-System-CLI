// Importing necessary classes for file handling, logging, and user input
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Main class for configuring and running a real-time ticketing system.
 * It allows users to configure event details, manage vendors, and customers, 
 * and simulate ticket selling in real time.
 */
public class Configuration {
    // Logger for logging system messages
    private static final Logger logger = Logger.getLogger(Configuration.class.getName());

    // Flag to track the running state of the system
    private static boolean isRunning = false;

    // Lists to manage threads for vendors and customers
    private static List<Thread> vendorThreads = new ArrayList<>();
    private static List<Thread> customerThreads = new ArrayList<>();

    // Ticket pool for managing ticket availability
    private static TicketPool ticketPool;

    // File to save configuration details in JSON format
    private static final String CONFIG_FILE = "configuration.json";

    /**
     * Main method to handle the configuration and operation of the ticketing system.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Display the welcome message and prompt for event details
        System.out.println("\n   🎟️ WELCOME TO REAL-TIME TICKETING SYSTEM 🎟️");
        System.out.println("--------------------------------------------------\n");
        System.out.println("---------------  EVENT DETAILS  ------------------\n");
        System.out.print("Enter The Event Name: ");
        String eventName = scanner.nextLine();
        BigDecimal ticketPrice = getTicketPriceInput(scanner);

        // Prompt for configuration settings
        System.out.println("\n---------------  CONFIGURATION  ------------------\n");
        int maxTickets = getRangedIntegerInput(scanner, "Enter The Total Number Of Tickets (1-1500):", 1, 1500);
        int ticketReleaseRate = getRangedIntegerInput(scanner, "Enter The Ticket Release Rate In Seconds (1-10):", 1, 10);
        int customerRetrievalRate = getRangedIntegerInput(scanner, "Enter The Customer Retrieval Rate In Seconds (1-10):", 1, 10);
        int maxCapacity = getMaxCapacityInput(scanner, maxTickets);

        // Prompt for the number of vendors and customers
        System.out.println("\n-------  ENTER NO OF VENDORS & CUSTOMERS  -------\n");
        int numVendors = getRangedIntegerInput(scanner, "Enter The Number Of Vendors (1-50):", 1, 50);
        int numCustomers = getRangedIntegerInput(scanner, "Enter The Number Of Customers (1-50):", 1, 50);

        // Save configuration details to a JSON file
        ConfigData configData = new ConfigData(eventName, ticketPrice, maxTickets, ticketReleaseRate, customerRetrievalRate, maxCapacity, numVendors, numCustomers);
        saveConfigData(configData);

        // Initialize the ticket pool with the maximum capacity
        ticketPool = new TicketPool(maxCapacity);

        // Display the menu and handle user choices
        while (true) {
            if (!isRunning) {
                System.out.println("===============================");
                System.out.println("         SYSTEM MENU           ");
                System.out.println("===============================");
                System.out.println("1️⃣ Start System");
                System.out.println("2️⃣ Stop System");
                System.out.println("3️⃣ Exit");
                System.out.print("\nPlease Enter Your Choice: ");
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.println("\n🎟️ System is now running! Vendors and customers are active.\n");
                    startSystem(numVendors, numCustomers, maxTickets, ticketReleaseRate, customerRetrievalRate, eventName, ticketPrice);
                    break;
                case 2:
                    stopSystem();
                    break;
                case 3:
                    stopSystem(); // Ensure threads are stopped before exiting
                    System.out.println("\n🚪 Exiting the system. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("\n❌ Invalid choice. Please select 1, 2, or 3.");
            }
        }
    }

    /**
     * Saves configuration data to a JSON file for future use.
     */
    private static void saveConfigData(ConfigData configData) {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            String jsonString = createJSONString(configData);
            writer.write(jsonString);
            System.out.println("Configuration data saved successfully to " + CONFIG_FILE);
        } catch (IOException e) {
            System.out.println("Error saving configuration data: " + e.getMessage());
        }
    }

    /**
     * Creates a JSON string representation of the ConfigData object.
     */
    private static String createJSONString(ConfigData configData) {
        return String.format("{\n" +
                        "  \"eventName\": \"%s\",\n" +
                        "  \"ticketPrice\": %s,\n" +
                        "  \"maxTickets\": %d,\n" +
                        "  \"ticketReleaseRate\": %d,\n" +
                        "  \"customerRetrievalRate\": %d,\n" +
                        "  \"maxCapacity\": %d,\n" +
                        "  \"numVendors\": %d,\n" +
                        "  \"numCustomers\": %d\n" +
                        "}",
                configData.getEventName(),
                configData.getTicketPrice(),
                configData.getMaxTickets(),
                configData.getTicketReleaseRate(),
                configData.getCustomerRetrievalRate(),
                configData.getMaxCapacity(),
                configData.getNumVendors(),
                configData.getNumCustomers());
    }

    /**
     * Starts the ticketing system by creating and starting vendor and customer threads.
     */
    private static void startSystem(int numVendors, int numCustomers, int maxTickets, int ticketReleaseRate, int customerRetrievalRate, String eventName, BigDecimal ticketPrice) {
        if (isRunning) {
            System.out.println("\n⚠️ System is already running!");
            return;
        }

        logger.warning("Starting the ticketing system...\n");
        isRunning = true;

        // Create and start vendor threads
        for (int i = 1; i <= numVendors; i++) {
            Vendor vendor = new Vendor(ticketPool, maxTickets / numVendors, ticketReleaseRate, eventName, ticketPrice);
            Thread vendorThread = new Thread(vendor, "Vendor-" + i);
            vendorThreads.add(vendorThread);
            vendorThread.start();
        }

        // Create and start customer threads
        for (int i = 1; i <= numCustomers; i++) {
            Customer customer = new Customer(ticketPool, customerRetrievalRate, maxTickets / numCustomers);
            Thread customerThread = new Thread(customer, "Customer-" + i);
            customerThreads.add(customerThread);
            customerThread.start();
        }
    }

    /**
     * Stops all active threads and pauses the system.
     */
    private static void stopSystem() {
        if (!isRunning) {
            System.out.println("\n⚠️ System is already stopped!\n");
            return;
        }

        logger.info("Stopping the ticketing system...");
        isRunning = false;

        // Interrupt vendor threads
        for (Thread vendorThread : vendorThreads) {
            vendorThread.interrupt();
            System.out.println("❌ " + vendorThread.getName() + " was interrupted!");
        }

        // Interrupt customer threads
        for (Thread customerThread : customerThreads) {
            customerThread.interrupt();
            System.out.println("❌ " + customerThread.getName() + " was interrupted!");
        }

        vendorThreads.clear();
        customerThreads.clear();
        System.out.println("\n✅ System stopped successfully.\n");
    }

    /**
     * Prompts the user to input an integer within a specified range.
     */
    private static int getRangedIntegerInput(Scanner scanner, String prompt, int min, int max) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine());
                if (value >= min && value <= max) {
                    break;
                } else {
                    System.out.println("❌ Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a valid number.");
            }
        }
        return value;
    }

    /**
     * Prompts the user to input a valid ticket price.
     */
    private static BigDecimal getTicketPriceInput(Scanner scanner) {
        BigDecimal value;
        while (true) {
            System.out.print("Enter The Ticket Price: ");
            try {
                value = new BigDecimal(scanner.nextLine());
                if (value.compareTo(BigDecimal.ZERO) > 0) { // Ensure price is positive
                    break;
                } else {
                    System.out.println("❌ Please enter a positive number for the ticket price.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a valid decimal number for the ticket price.");
            }
        }
        return value;
    }

    /**
     * Ensures the maximum ticket pool capacity is valid.
     */
    private static int getMaxCapacityInput(Scanner scanner, int maxTickets) {
        int maxCapacity;
        while (true) {
            maxCapacity = getRangedIntegerInput(scanner, "Enter The Maximum Ticket Pool Capacity (1-1500):", 1, 1500);
            if (maxCapacity >= maxTickets) {
                break;
            } else {
                System.out.println("❌ Maximum capacity cannot be less than the total number of tickets (" + maxTickets + ").");
            }
        }
        return maxCapacity;
    }
}
