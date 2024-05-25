/*
*************                  Sakarya University                                ****************
*************       SWE 204 - Concepts Of Programming Languages Homework 2       ***************
*************                     OBADA SMAISEM                                  ***************
*************                       B221202558                                   ***************
 */
package org.example;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to choose which version of the code to run
        System.out.println("Choose which version of the code to run:");
        System.out.println("1. Read requests from a single input file.");
        System.out.println("2. Read requests from all input files (1-4).");
        int choice = scanner.nextInt();

        // Execute the chosen version of the code
        switch (choice) {
            case 1:
                runSingleFileVersion();
                break;
            case 2:
                runMultipleFilesVersion();
                break;
            default:
                System.err.println("Invalid choice. Exiting...");
        }

        scanner.close();
    }

    // Method to run the version of the code that reads requests from a single input file
    private static void runSingleFileVersion() {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the input file number
        System.out.println("Enter the input file number (1-4): ");
        int fileNumber = scanner.nextInt();

        // Validate the input file number
        if (fileNumber < 1 || fileNumber > 4) {
            System.err.println("Invalid input file number. Please enter a number between 1 and 4.");
            return;
        }

        // Construct file names for input, output, and log files
        String inputFileName = "input" + fileNumber + ".txt";
        String outputFileName = "output" + fileNumber + ".txt";
        String logFileName = "log" + fileNumber + ".txt";

        // Clear the previous output and log files
        clearFile(outputFileName);
        clearFile(logFileName);

        // Read requests from the input file
        List<Request> requests = readRequestsFromFile(inputFileName);
        if (requests == null) {
            System.err.println("Failed to read requests from file: " + inputFileName);
            return;
        }

        // Process the requests using a RequestHandler
        RequestHandler requestHandler = new RequestHandler(requests, outputFileName, logFileName);
        requestHandler.processRequests();

        scanner.close();
    }

    // Method to run the version of the code that reads requests from all input files (1-4)
    private static void runMultipleFilesVersion() {
        // Loop through input files from 1 to 4
        for (int fileNumber = 1; fileNumber <= 4; fileNumber++) {
            // Construct file names for input, output, and log files
            String inputFileName = "input" + fileNumber + ".txt";
            String outputFileName = "output" + fileNumber + ".txt";
            String logFileName = "log" + fileNumber + ".txt";

            // Clear the previous output and log files
            clearFile(outputFileName);
            clearFile(logFileName);

            // Read requests from the input file
            List<Request> requests = readRequestsFromFile(inputFileName);
            if (requests == null) {
                System.err.println("Failed to read requests from file: " + inputFileName);
                continue; // Continue to the next file if reading fails
            }

            // Process the requests using a RequestHandler
            RequestHandler requestHandler = new RequestHandler(requests, outputFileName, logFileName);
            requestHandler.processRequests();
        }
    }

    // Method to read requests from a file
    private static List<Request> readRequestsFromFile(String fileName) {
        List<Request> requests = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                // Split the line into parts using whitespace as delimiter
                String[] parts = line.split("\\s+");
                if (parts.length != 3) {
                    System.err.println("Malformed line: " + line);
                    continue; // Skip malformed lines
                }
                int requestTime;
                int outputSize;
                try {
                    // Parse request time and output size from the parts
                    requestTime = Integer.parseInt(parts[0]);
                    outputSize = Integer.parseInt(parts[2]);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format in line: " + line);
                    continue; // Skip lines with invalid numbers
                }
                String patternType = parts[1];
                // Create a new Request object and add it to the list
                requests.add(new Request(requestTime, patternType, outputSize));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Return null if there's an error reading the file
        }
        return requests;
    }

    // Method to clear the content of a file
    private static void clearFile(String fileName) {
        try {
            new PrintWriter(fileName).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
