/*
 *************                  Sakarya University                                ****************
 *************       SWE 204 - Concepts Of Programming Languages Homework 2       ***************
 *************                     OBADA SMAISEM                                  ***************
 *************                       B221202558                                   ***************
 */

package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Printer {
    private final BufferedWriter writer; // BufferedWriter to write pattern output to a file

    // Constructor to initialize the printer with the specified output file name
    public Printer(String outputFileName) throws IOException {
        // Create a BufferedWriter to write to the output file
        this.writer = new BufferedWriter(new FileWriter(outputFileName));
    }

    // Method to print a pattern based on the pattern type and size
    // Synchronized to ensure thread safety when writing to the output file
    public synchronized void printPattern(String patternType, int size) throws IOException {
        // StringBuilder to construct the pattern
        StringBuilder pattern = new StringBuilder();
        // Check the pattern type and construct the pattern accordingly
        if (patternType.equalsIgnoreCase("Star")) {
            for (int i = 0; i < size; i++) {
                // Append spaces to align the pattern
                for (int j = 0; j < size - i - 1; j++) {
                    pattern.append("  ");
                }
                // Append '*' characters for the pattern
                for (int j = 0; j < 2 * i + 1; j++) {
                    pattern.append("* ");
                }
                // Move to the next line
                pattern.append("\n");
            }
        } else if (patternType.equalsIgnoreCase("Alphabet")) {
            char letter = 'A'; // Start with the letter 'A'
            for (int i = 0; i < size; i++) {
                // Append spaces to align the pattern
                for (int j = 0; j < size - i - 1; j++) {
                    pattern.append("  ");
                }
                // Construct the alphabet pattern
                char currentChar = (char) (letter + i);
                for (int j = 0; j < 2 * i + 1; j++) {
                    pattern.append(currentChar).append(" ");
                    if (j < i) currentChar++;
                    else currentChar--;
                }
                // Move to the next line
                pattern.append("\n");
            }
        }
        // Write the pattern to the output file
        writer.write(pattern.toString());
        // Flush the BufferedWriter to ensure the pattern is written immediately
        writer.flush();
    }

    // Method to close the output file
    public void close() throws IOException {
        // Close the BufferedWriter
        writer.close();
    }
}
