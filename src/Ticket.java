import java.math.BigDecimal;

/**
 * Represents a ticket in the real-time ticketing system.
 * Each ticket is associated with an event, has a unique ID, and has a specific price.
 */
public class Ticket {
    // Unique identifier for the ticket
    private int ticketId;

    // Name of the event for which the ticket is issued
    private String eventName;

    // Price of the ticket
    private BigDecimal ticketPrice;

    /**
     * Constructs a Ticket instance with the given details.
     *
     * @param ticketId    The unique identifier for the ticket.
     * @param eventName   The name of the event.
     * @param ticketPrice The price of the ticket.
     */
    public Ticket(int ticketId, String eventName, BigDecimal ticketPrice) {
        this.ticketId = ticketId;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
    }

    /**
     * Gets the name of the event associated with the ticket.
     *
     * @return The event name.
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Sets the name of the event associated with the ticket.
     *
     * @param eventName The new event name.
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Gets the unique identifier of the ticket.
     *
     * @return The ticket ID.
     */
    public int getTicketId() {
        return ticketId;
    }

    /**
     * Sets the unique identifier of the ticket.
     *
     * @param ticketId The new ticket ID.
     */
    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    /**
     * Gets the price of the ticket.
     *
     * @return The ticket price.
     */
    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    /**
     * Sets the price of the ticket.
     *
     * @param ticketPrice The new ticket price.
     */
    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    /**
     * Provides a string representation of the ticket, including ID, event name, and price.
     *
     * @return A formatted string with ticket details.
     */
    @Override
    public String toString() {
        return "\nTicket Details: " +
                "ID = " + ticketId +
                "  |  Event Name = " + eventName +
                "  |  Price = " + ticketPrice + "\n";
    }
}