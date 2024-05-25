/*
 *************                  Sakarya University                                ****************
 *************       SWE 204 - Concepts Of Programming Languages Homework 2       ***************
 *************                     OBADA SMAISEM                                  ***************
 *************                       B221202558                                   ***************
 *************            https://github.com/USM279/PDP_HW2.git                   ***************

 */


package org.example;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

public class RequestHandler {
    private final List<Request> requests; // List of print requests
    private final Printer printer; // Printer instance to print patterns
    private final Logger logger; // Logger instance to log events
    private final ExecutorService executorService; // ExecutorService for handling concurrent requests
    private final BlockingQueue<Request> requestQueue; // Blocking queue to hold print requests

    private String currentPatternType = null; // Current pattern type being processed

    // Constructor to initialize the RequestHandler with requests, output file name, and log file name
    public RequestHandler(List<Request> requests, String outputFileName, String logFileName) {
        this.requests = requests;
        this.executorService = Executors.newCachedThreadPool(); // Create a thread pool
        this.requestQueue = new LinkedBlockingQueue<>(); // Create a blocking queue for requests
        Printer tempPrinter = null;
        Logger tempLogger = null;
        try {
            tempPrinter = new Printer(outputFileName); // Create a printer
            tempLogger = new Logger(logFileName); // Create a logger
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.printer = tempPrinter; // Initialize the printer
        this.logger = tempLogger; // Initialize the logger
    }

    // Method to process print requests
    public void processRequests() {
        // Process each request in the list
        for (Request request : requests) {
            try {
                // Delay processing based on request time
                Thread.sleep(request.getRequestTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Submit request processing to the executor service
            executorService.submit(() -> {
                try {
                    requestQueue.put(request); // Add request to the blocking queue
                    processRequest(); // Process the request
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        // Shut down the executor service after processing all requests
        executorService.shutdown();
        try {
            // Wait for the executor service to terminate
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                // Force shutdown if it takes longer than 60 seconds
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            // Force shutdown if interrupted
            executorService.shutdownNow();
        }

        // Close the printer and logger after processing all requests
        try {
            printer.close();
            logger.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to process a single request
    private synchronized void processRequest() {
        Request request;
        try {
            // Retrieve the request from the blocking queue
            request = requestQueue.take();
            // Check if the current pattern type matches the request pattern type
            if (currentPatternType == null || currentPatternType.equals(request.getPatternType())) {
                currentPatternType = request.getPatternType(); // Update the current pattern type
                // Log and print the pattern
                logger.log("Printing is started for " + request.getPatternType() + "-" + request.getOutputSize());
                printer.printPattern(request.getPatternType(), request.getOutputSize());
                logger.log("Printing is done for " + request.getPatternType() + "-" + request.getOutputSize());
                logger.log("Printer is free");
            } else {
                // Re-add the request to the queue if the pattern type doesn't match
                requestQueue.put(request);
                return;
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        currentPatternType = null; // Reset the current pattern type
    }
}
