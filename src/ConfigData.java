import java.math.BigDecimal;
/**
 * Represents the configuration data for the real-time ticketing system.
 * This class stores the event details and system settings, such as the
 * number of tickets, ticket release rate, and number of vendors and customers.
 */
public class ConfigData {
    // The name of the event
    private String eventName;

    // The price of a single ticket
    private BigDecimal ticketPrice;

    // The maximum number of tickets available for the event
    private int maxTickets;

    // The rate (in seconds) at which tickets are released
    private int ticketReleaseRate;

    // The rate (in seconds) at which customers retrieve tickets
    private int customerRetrievalRate;

    // The maximum capacity of the ticket pool
    private int maxCapacity;

    // The number of vendors participating in the system
    private int numVendors;

    // The number of customers participating in the system
    private int numCustomers;


    public ConfigData(String eventName, BigDecimal ticketPrice, int maxTickets, int ticketReleaseRate, int customerRetrievalRate, int maxCapacity, int numVendors, int numCustomers) {
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
        this.maxTickets = maxTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxCapacity = maxCapacity;
        this.numVendors = numVendors;
        this.numCustomers = numCustomers;
    }

    // Getters to provide access to the private fields

    /**
     * Gets the name of the event.
     * @return The event name.
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Gets the ticket price.
     * @return The ticket price.
     */
    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    /**
     * Gets the maximum number of tickets available.
     * @return The maximum number of tickets.
     */
    public int getMaxTickets() {
        return maxTickets;
    }

    /**
     * Gets the ticket release rate in seconds.
     * @return The ticket release rate.
     */
    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    /**
     * Gets the customer retrieval rate in seconds.
     * @return The customer retrieval rate.
     */
    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    /**
     * Gets the maximum capacity of the ticket pool.
     * @return The maximum capacity.
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * Gets the number of vendors.
     * @return The number of vendors.
     */
    public int getNumVendors() {
        return numVendors;
    }

    /**
     * Gets the number of customers.
     * @return The number of customers.
     */
    public int getNumCustomers() {
        return numCustomers;
    }
}
