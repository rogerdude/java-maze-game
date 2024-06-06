import display.GuiDisplay;
import display.TextDisplay;
import exceptions.MazeMalformedException;
import exceptions.MazeSizeMissmatchException;

import io.FileLoader;
import maze.AutoNavigator;
import maze.Maze;

import java.io.FileNotFoundException;

/**
 * A class that executes the maze based on user input from command line.
 */
public class Launcher {
    /** The constant to compare the 'GUI' command in the command line arguments */
    private static final String GUI_ARG = "GUI";

    /** A variable to specify whether GUI has been specified in the command line arguments */
    private static boolean gui;

    /**
     * Checks and validates the command line arguments
     *
     * @param args an array of the command line arguments
     * @return the filename if it was specified
     * @throws IllegalArgumentException if the command line arguments do not match required format
     */
    public static String checkCommandLineArgs(String[] args) throws IllegalArgumentException {
        if (args.length == 0 || args.length > 2) {
            throw new IllegalArgumentException();
        }

        // Check if 'GUI' has been specified.
        if (args[0].compareTo(GUI_ARG) == 0) {
            gui = true;
        }

        if (gui) {
            if (args.length == 1) {
                return null;
            }
            return args[1];
        }
        return args[0];
    }

    /**
     * Runs the maze either in GUI or terminal/
     *
     * @param args the command line arguments with the required parameters.
     * @throws FileNotFoundException if filename in args is invalid
     * @throws MazeSizeMissmatchException if size specified in line 1 of maze file does not match
     *         the overall length of maze.
     * @throws MazeMalformedException if format of maze does not match required format.
     * @throws IllegalArgumentException if maze contains invalid characters.
     * @throws InterruptedException if Thread.sleep() is interrupted while sleeping
     */
    public static void main(String[] args) throws FileNotFoundException, MazeSizeMissmatchException,
            MazeMalformedException, IllegalArgumentException, InterruptedException {

        // Validate command line arguments and initialise maze based on specified file.
        gui = false;
        String filename = checkCommandLineArgs(args);
        FileLoader mazeFile = new FileLoader();
        char[][] mazeData = mazeFile.load(filename);
        Maze maze = new Maze(mazeData);

        // Open GUI if user has specified on command line arguments, otherwise display and
        // use auto-navigator maze in terminal.
        if (gui) {
            GuiDisplay guiDisplay = new GuiDisplay(maze, filename);
        } else {
            TextDisplay textDisplay = new TextDisplay(maze);
            textDisplay.displayMaze();

            AutoNavigator auto = new AutoNavigator(maze, textDisplay);
            auto.navigate();

            if (maze.isGameOver()) {
                System.out.print("Exit was found!");
            } else {
                System.out.println("Exit was not found.");
            }
        }
    }
}
