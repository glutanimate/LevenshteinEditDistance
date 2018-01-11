package com.glutanimate.editdistance;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * <b>EditDistance class, performs calculations</b>
 *
 * Takes two strings as its arguments and calculates the
 * editing distance using the Levenshtein algorithm.
 *
 * The result is printed both as an editing distance matrix
 * and as the value of the minimum editing distance.
 * */
class EditDistance {
    /*
    * TODO: visualize actual moves ← need to fully understand algorithm first
    * */

    private String string1;
    private String string2;

    /**
     * Object constructor
     * */
    EditDistance (String string1, String string2) {
        this.string1 = string1;
        this.string2 = string2;
        calculate();

    }

    /**
     * Simple result data class
     *
     * Can't simply return tuples of multiple data types in JAVA. Looks
     * like the best practice is returning a data class.
     */
    class DistanceData {
        final String matrix;
        final int distance;

        DistanceData(String matrix, int distance) {
            this.matrix = matrix;
            this.distance = distance;
        }
    }

    /**
     * Main editing distance calculation method, returns DistanceData object
     */
    DistanceData calculate() {
        // calculate rows and cols for editing distance matrix:
        int cols = string1.length() + 1;
        int rows = string2.length() + 1; // n+1 rows and cols needed

        // create matrix as an integer array
        int[][] arr = new int[rows][cols]; // arrays in Java use a static size

        // set up first column and first row:
        for (int i = 0; i < cols; i++) {
            arr[0][i] = i;
        }

        for (int i = 0; i < rows; i++) {
            arr[i][0] = i;
        }

        // User StringWriter and PrintWriter to compose multi-line string
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter, true);


        // Compose title row and first matrix row
        // TODO: find a more elegant way to do this
        String[] string1Arr = string1.split("");
        string1Arr[0] = " ";
        String title = "  " + Arrays.toString(string1Arr);
        String titleFormatted = title.replaceAll("[\\[|\\]|\\,]", " ");
        writer.println(titleFormatted);
        writer.println("  " + Arrays.toString(arr[0]));

        // for all other columns and rows loop over each individual cell:
        for (int row = 1; row < rows; row++) {

            // get corresponding string characters:
            char s2_char = string2.charAt(row - 1);

            for (int col = 1; col < cols; col++) {

                // get corresponding string characters:
                char s1_char = string1.charAt(col - 1);

                int cost;

                // if chars are identical use 0 cost for algorithm
                if (s1_char == s2_char) {
                    cost = 0;
                } else {
                    cost = 1;
                }

                // calculate individual costs of all available moves
                int deletion_cost = arr[row-1][col] + 1;
                int substitution_cost = arr[row-1][col-1] + cost;
                int insertion_cost = arr[row][col-1] + 1;

                // find the lowest cost of the three:
                // note that Math.min() can only compare two values at a time
                arr[row][col] = Math.min(Math.min(deletion_cost, substitution_cost), insertion_cost);

            }
            // print a string representation of each row, preceded by each relevant character:
            writer.println(string2.charAt(row - 1) + " " + Arrays.toString(arr[row]));
        }

        // the last cell corresponds to the minimum editing distance between the two strings:
        int distance = arr[rows-1][cols-1];

        String matrix = stringWriter.toString();

        // return new DistanceData object
        return (new DistanceData(matrix, distance));
    }

}
