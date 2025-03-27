# Real-Time Ticketing System CLI

This project implements a command-line interface (CLI) for a real-time ticketing system. It simulates a scenario where multiple vendors release tickets, and multiple customers try to purchase them concurrently. The system is designed to demonstrate multithreading, synchronization, and basic file I/O in Java.

## Features

-   Simulates a real-time ticketing system with vendors and customers.
-   Multithreaded to handle concurrent vendors releasing tickets and customers purchasing tickets.
-   Uses a shared `TicketPool` to manage ticket availability.
-   Allows configuration of system parameters:
    -   Event name
    -   Ticket price
    -   Total number of tickets
    -   Ticket release rate (for vendors)
    -   Customer retrieval rate (for customers)
    -   Maximum ticket pool capacity
    -   Number of vendors
    -   Number of customers
-   Saves the initial configuration to a `configuration.json` file.
-   Logs system events (ticket addition, current ticket status, vendor ticket count, ticket details, and ticket purchase) to `tickets_logs.log`.

## Prerequisites

-   **Java:** Version 21.0.2 (JDK)
-   **Node.js:** Version v22.11.0 (Although Node.js is listed, it is not directly used by the Java code. Perhaps you are using it for a separate part of your project setup that's not included in the provided code)
-   **IDE:** A Java IDE (e.g., IntelliJ IDEA) is recommended for easier development and building.

## Running the Application

**Using an IDE:**

1. Open the project in your Java IDE.
2. Locate the `Configuration` class.
3. Right-click on the `Configuration` class and select "Run".

## Configuration

On the first run, the application prompts you to enter the following configuration parameters:

### Event Details:

-   **Event Name:** The name of the event (e.g., "Concert", "Movie Night").
-   **Ticket Price:** The price of a single ticket.

### System Configuration:

-   **Total Number of Tickets:** The total number of tickets to be released.
-   **Ticket Release Rate (seconds):** The delay between each ticket release by a vendor.
-   **Customer Retrieval Rate (seconds):** The delay between each customer's attempt to purchase a ticket.
-   **Maximum Ticket Pool Capacity:** The maximum number of tickets that the `TicketPool` can hold simultaneously.

### Vendors & Customers:

-   **Number of Vendors:** The number of vendor threads (simulated vendors).
-   **Number of Customers:** The number of customer threads (simulated customers).

These configurations are saved in `configuration.json` for future runs.

## Usage

After entering the configuration, the main menu is displayed:

-   **1️⃣ Start System:** Starts the simulation. Vendors begin releasing tickets, and customers begin attempting to purchase them.
-   **2️⃣ Stop System:** Stops the simulation. This interrupts all running vendor and customer threads, causing them to terminate gracefully.
-   **3️⃣ Exit:** Exits the application. If the system is running, it will be stopped first.

## Stopping the System

You can stop the system at any time by choosing option **2️⃣ Stop System** from the main menu. The system will then return to the main menu.

## Logs

The application logs events to the `tickets_logs.log` file. Log entries include:

-   System start and stop messages.
-   Ticket addition events (by vendors) with:
    -   Ticket details (ID, event name, price)
    -   Current ticket pool size
    -   Vendor ticket count
-   Ticket purchase events (by customers) with ticket details.
-   Warnings when the ticket pool is full or empty.
-   Thread interruption messages.

## JSON Configuration

The `configuration.json` file stores the initial configuration in JSON format. Example:

```json
{
  "eventName": "Example Event",
  "ticketPrice": 25.00,
  "maxTickets": 500,
  "ticketReleaseRate": 2,
  "customerRetrievalRate": 3,
  "maxCapacity": 100,
  "numVendors": 5,
  "numCustomers": 10
}
