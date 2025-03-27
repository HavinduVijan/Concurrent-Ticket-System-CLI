import java.util.logging.Logger;

/**
 * Represents a customer in the real-time ticketing system.
 * Each customer attempts to purchase a specified quantity of tickets
 * at a defined retrieval rate from the shared TicketPool.
 */
public class Customer implements Runnable {
    private static final Logger logger = Logger.getLogger(Customer.class.getName());

    // The shared TicketPool from which the customer will purchase tickets
    private TicketPool ticketPool;

    // The rate (in seconds) at which the customer retrieves tickets
    private int customerRetrievalRate;

    // The total number of tickets the customer aims to purchase
    private int quantity;

    /**
     * Constructs a Customer instance with the given ticket pool, retrieval rate, and ticket quantity.
     *
     * @param ticketPool           The shared TicketPool instance.
     * @param customerRetrievalRate The rate at which tickets are retrieved (in seconds).
     * @param quantity             The number of tickets the customer wants to purchase.
     */
    public Customer(TicketPool ticketPool, int customerRetrievalRate, int quantity) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.quantity = quantity;
    }

    /**
     * The main logic of the Customer class, executed when the thread starts.
     * The customer attempts to purchase the specified number of tickets, waiting
     * for the defined retrieval rate between purchases. If interrupted, the
     * customer exits gracefully.
     */
    @Override
    public void run() {
        // Loop through the number of tickets the customer wants to purchase
        for (int i = 0; i < quantity; i++) {
            // Check if the thread has been interrupted
            if (Thread.currentThread().isInterrupted()) {
                logger.warning(Thread.currentThread().getName() + " is interrupted. Exiting.");
                return; // Exit the loop gracefully
            }

            // Attempt to buy a ticket from the ticket pool
            ticketPool.buyTicket();

            // Wait for the defined retrieval rate before the next attempt
            try {
                Thread.sleep(customerRetrievalRate * 1000);
            } catch (InterruptedException e) {
                // Handle interruption gracefully
                logger.warning(Thread.currentThread().getName() + " was interrupted.");
                Thread.currentThread().interrupt(); // Re-interrupt the thread
                return; // Exit the loop gracefully
            }
        }

        // Log successful completion of ticket purchases
        System.out.println("✔️ " + Thread.currentThread().getName() + " completed all purchases!");
    }
}
