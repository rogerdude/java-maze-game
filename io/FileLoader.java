package io;

import exceptions.MazeMalformedException;
import exceptions.MazeSizeMissmatchException;

import java.io.*;

/**
 * A class that reads a file with maze data and loads into a 2D char array
 */
public class FileLoader implements FileInterface {
    /** An array of the valid characters in a maze file */
    private static final char[] VALID_LETTERS = {'#', ' ', '.', 'S', 'E'};

    /**
     * Gets the size of the maze from the first line of the maze file
     *
     * @param line is the fist line from the maze file
     * @return an array of two integers where first one is 'y' length, and second is 'x' length
     * @throws MazeMalformedException if the numbers on first line cannot be converted
     *              to integers, or if either given number is even.
     */
    private int[] getMazeSize(String line) throws MazeMalformedException {
        int[] mazeSize = new int[2];

        String[] lineElements = line.split(" ");
        if (lineElements.length != 2) {
            throw new MazeMalformedException();
        }

        // Convert number in line to an integer to get size of maze.
        try {
            mazeSize[0] = Integer.parseInt(lineElements[0]);
            mazeSize[1] = Integer.parseInt(lineElements[1]);
        } catch (NumberFormatException ex) {
            throw new MazeMalformedException();
        }

        // Check if maze x and y lengths are odd numbers.
        for (int num = 0; num < 2; num++) {
            if (mazeSize[num] % 2 == 0) {
                throw new MazeMalformedException();
            }
        }

        return mazeSize;
    }

    /**
     * Checks if all the characters in the specified line are valid.
     *
     * @param line a line from the maze file to validate
     * @throws IllegalArgumentException if the line has an invalid character
     */
    private void checkLine(String line) throws IllegalArgumentException {
        // Iterate through each character in the line.
        int lineLength = line.length();
        for (int i = 0; i < lineLength; i++) {
            // Check if character is in VALID_LETTERS
            boolean valid = false;
            for (int j = 0; j < 5; j++) {
                if (line.charAt(i) == VALID_LETTERS[j]) {
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * Loads a file with maze data and converts it to a 2D char array.
     *
     * @param filename The path to the maze file to be loaded.
     * @return a 2D array with all data from the maze file.
     * @throws MazeMalformedException if any line in maze data has incorrect format
     * @throws MazeSizeMissmatchException if size of line or number of lines in maze file does
     *              not match given length
     * @throws IllegalArgumentException if the maze file has any invalid characters
     * @throws FileNotFoundException if the filename cannot be found
     */
    @Override
    public char[][] load(String filename) throws MazeMalformedException,
            MazeSizeMissmatchException, IllegalArgumentException, FileNotFoundException {

        if (filename == null) {
            char[][] mazeData = new char[1][1];
            mazeData[0][0] = ' ';
            return mazeData;
        }

        int mazeX = 0;
        int mazeY = 0;
        char[][] mazeData = new char[0][];

        try (FileReader reader = new FileReader(filename)) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            int numOfStart = 0;
            int numOfEnd = 0;

            // Iterate through each line in the file.
            for (int lineNumber = 0; (line = bufferedReader.readLine()) != null; lineNumber++) {
                if (lineNumber == 0) {
                    // Get size of maze from first line
                    int[] mazeSize = getMazeSize(line);
                    mazeY = mazeSize[0];
                    mazeX = mazeSize[1];
                    mazeData = new char[mazeY][mazeX];

                } else {
                    // Check if size of line matches the given x and y lengths from line 1
                    int length = line.length();
                    if (length != mazeX || lineNumber > mazeY) {
                        throw new MazeSizeMissmatchException();
                    }

                    // Iterate through each char in the line and put it in mazeData.
                    for (int i = 0; i < length; i++) {
                        // Check if line has valid characters
                        checkLine(line);
                        if (line.charAt(i) == 'S') {
                            numOfStart++;
                        } else if (line.charAt(i) == 'E') {
                            numOfEnd++;
                        }
                        mazeData[lineNumber - 1][i] = line.charAt(i);
                    }
                }
            }
            bufferedReader.close();
            reader.close();
            // Check if number of 'S' and 'E' is written only once in the file.
            if (numOfStart != 1 || numOfEnd != 1) {
                throw new MazeMalformedException();
            }
        } catch (IOException ex) {
            throw new FileNotFoundException();
        }

        return mazeData;
    }
}
