import java.math.BigDecimal;
import java.util.logging.Logger;

/**
 * Represents a vendor in the real-time ticketing system.
 * Each vendor is responsible for releasing a specified number of tickets
 * at a defined release rate into the shared TicketPool.
 */
public class Vendor implements Runnable {
    private static final Logger logger = Logger.getLogger(Vendor.class.getName());

    // The shared TicketPool where tickets will be added
    private TicketPool ticketPool;

    // The total number of tickets the vendor will release
    private int totalTickets;

    // The rate (in seconds) at which tickets are released
    private int ticketReleaseRate;

    // The name of the event for which tickets are being sold
    private String eventName;

    // The price of each ticket being sold
    private BigDecimal ticketPrice;


    public Vendor(TicketPool ticketPool, int totalTickets, int ticketReleaseRate, String eventName, BigDecimal ticketPrice) {
        this.ticketPool = ticketPool;
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
    }

    /**
     * The main logic of the Vendor class, executed when the thread starts.
     * The vendor adds the specified number of tickets to the TicketPool, waiting
     * for the defined release rate between each addition. If interrupted, the
     * vendor exits gracefully.
     */
    @Override
    public void run() {
        // Loop through the total number of tickets to be released
        for (int i = 1; i <= totalTickets; i++) {
            // Check if the thread has been interrupted
            if (Thread.currentThread().isInterrupted()) {
                logger.warning(Thread.currentThread().getName() + " is interrupted. Exiting.");
                return; // Exit the loop gracefully
            }

            // Create a new ticket and add it to the TicketPool
            Ticket ticket = new Ticket(0, eventName, ticketPrice);
            ticketPool.addTickets(ticket);

            // Wait for the defined release rate before releasing the next ticket
            try {
                Thread.sleep(ticketReleaseRate * 1000);
            } catch (InterruptedException e) {
                // Handle interruption gracefully
                logger.warning(Thread.currentThread().getName() + " was interrupted.");
                Thread.currentThread().interrupt(); // Re-interrupt the thread
                return; // Exit the loop gracefully
            }
        }

        // Log successful completion of ticket release
        System.out.println("✔️ " + Thread.currentThread().getName() + " finished releasing all tickets!");
    }
}
