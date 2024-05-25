/*
 *************                  Sakarya University                                ****************
 *************       SWE 204 - Concepts Of Programming Languages Homework 2       ***************
 *************                     OBADA SMAISEM                                  ***************
 *************                       B221202558                                   ***************
 *************            https://github.com/USM279/PDP_HW2.git                   ***************

 */


package org.example;

// Class representing a print request
public class Request {
    // Attributes of a print request
    private final int requestTime;   // Time when the request was made
    private final String patternType;   // Type of pattern to print (e.g., "Star" or "Alphabet")
    private final int outputSize;    // Size of the output pattern

    // Constructor to initialize a print request with the specified attributes
    public Request(int requestTime, String patternType, int outputSize) {
        this.requestTime = requestTime;
        this.patternType = patternType;
        this.outputSize = outputSize;
    }

    // Getter method to retrieve the request time
    public int getRequestTime() {
        return requestTime;
    }

    // Getter method to retrieve the pattern type
    public String getPatternType() {
        return patternType;
    }

    // Getter method to retrieve the output size
    public int getOutputSize() {
        return outputSize;
    }
}
