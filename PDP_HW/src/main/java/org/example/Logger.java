/*
 *************                  Sakarya University                                ****************
 *************       SWE 204 - Concepts Of Programming Languages Homework 2       ***************
 *************                     OBADA SMAISEM                                  ***************
 *************                       B221202558                                   ***************
 *************            https://github.com/USM279/PDP_HW2.git                   ***************

 */

package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private final BufferedWriter logWriter; // BufferedWriter to write log messages to a file

    // Constructor to initialize the logger with the specified log file name
    public Logger(String logFileName) throws IOException {
        // Create a BufferedWriter to write to the log file
        this.logWriter = new BufferedWriter(new FileWriter(logFileName));
    }

    // Method to log a message
    // Synchronized to ensure thread safety when writing to the log file
    public synchronized void log(String message) throws IOException {
        // Write the message to the log file
        logWriter.write(message);
        // Move to the next line
        logWriter.newLine();
        // Flush the BufferedWriter to ensure the message is written immediately
        logWriter.flush();
    }

    // Method to close the log file
    public void close() throws IOException {
        // Close the BufferedWriter
        logWriter.close();
    }
}
