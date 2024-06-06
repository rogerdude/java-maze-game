package display;

import maze.Maze;

/**
 * A class that initialises the display in the terminal to visualise and navigate the maze.
 */
public class TextDisplay {
    /** The unicode character to print a square on the terminal */
    private static final String SQUARE = "\u2588" + "\u001B[0m";

    /** The instance of Maze which contains all the maze data */
    private Maze maze;

    /**
     * The constructor for the TextDisplay class
     *
     * @param maze an instance of Maze to display and navigate
     */
    public TextDisplay(Maze maze) {
        this.maze = maze;
    }

    /**
     * Prints all components of the maze onto the terminal.
     */
    public void displayMaze() {
        int mazeX = this.maze.getMazeX();
        int mazeY = this.maze.getMazeY();

        // Make each maze row into a string
        StringBuilder line = new StringBuilder();
        for (int y = 0; y < mazeY; y++) {
            for (int x = 0; x < mazeX; x++) {
                line.append(this.maze.getElement(x, y).getTextColour());
                line.append(SQUARE);
            }
            System.out.println(line);
            line = new StringBuilder();
        }
        System.out.print('\n');
    }
}
