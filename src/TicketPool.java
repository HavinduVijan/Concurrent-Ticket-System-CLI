import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TicketPool {
    private static final Logger logger = Logger.getLogger(TicketPool.class.getName());
    private Queue<Ticket> ticketQueue;
    private int maxCapacity;
    private static AtomicInteger ticketIdGenerator = new AtomicInteger(0);

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.ticketQueue = new LinkedList<>();
        configureFileLogger();
        System.out.println("\n🎟️ Ticket Pool initialized with maximum capacity: " + maxCapacity + " tickets 🎟️\n");
    }

    private void configureFileLogger() {
        try {
            // FileHandler to log to a file
            FileHandler fileHandler = new FileHandler("tickets_logs.log", true); // Append mode
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

            // Set the level for the logger
            logger.setLevel(Level.ALL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void addTickets(Ticket ticket) {
        while (ticketQueue.size() >= maxCapacity) {
            try {
                logger.warning("⛔ " + Thread.currentThread().getName() + " is waiting: Ticket pool is FULL.\n");
                wait();
            } catch (InterruptedException e) {
                logger.warning(Thread.currentThread().getName() + " was interrupted.");
                Thread.currentThread().interrupt(); // Re-interrupt the thread
                return; // Exit the loop gracefully
            }
        }

        int ticketId = ticketIdGenerator.incrementAndGet();
        ticket.setTicketId(ticketId);

        this.ticketQueue.add(ticket);
        notifyAll();
        logger.info(Thread.currentThread().getName() + " has added 1 ticket to TicketPool.\nCurrent Ticket Status : " + ticketQueue.size() +"    |    "+"Vendor Ticket Count : "+ticketId+"\n" );
    }

    public synchronized Ticket buyTicket() {
        while (ticketQueue.isEmpty()) {
            try {
                logger.warning("⏳ " + Thread.currentThread().getName() + " is waiting: Ticket pool is EMPTY.\n");
                wait();
            } catch (InterruptedException e) {
                logger.warning(Thread.currentThread().getName() + " was interrupted.");
                Thread.currentThread().interrupt(); // Re-interrupt the thread
                return null; // Exit the loop gracefully
            }
        }

        Ticket ticket = ticketQueue.poll(); // remove ticket from queue
        notifyAll();
        logger.info(Thread.currentThread().getName() + " has purchased 1 ticket from TicketPool.\nCurrent Ticket Status :" + ticketQueue.size() +ticket);
        return ticket;
    }
}