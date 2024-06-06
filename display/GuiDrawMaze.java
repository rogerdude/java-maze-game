package display;

import maze.Maze;
import javax.swing.*;
import java.awt.*;

/**
 * A class to draw the maze and contain the menu components.
 */
public class GuiDrawMaze extends JPanel {
    /** The initial width for each rectangle for each component of maze */
    public static final int INITIAL_WIDTH = 25;

    /** The initial height for each rectangle for each component of maze */
    public static final int INITIAL_HEIGHT = 30;

    /** The offset from the left edge of the frame for where to start drawing */
    public static final int OFFSET_X = 30;

    /** The offset from the top edge of the frame for where to start drawing */
    public static final int OFFSET_Y = 40;

    /** The clearance from the edges for where to stop drawing */
    public static final int CLEARANCE = 100;

    /** The Maze object containing the 2D array of MazeComponent to display */
    private Maze maze;

    /** The width of each rectangle for each component of the maze */
    private int sizeX;

    /** The height of each rectangle for each component of the maze */
    private int sizeY;

    /** The main frame for the GUI */
    private JFrame frame;

    /**
     * Constructor to initialise the maze, main frame, and initial heights and widths for
     * the rectangle for components.
     *
     * @param maze an instance of Maze containing all maze data and components
     * @param frame the main frame of the GUI to get its height and width
     */
    public GuiDrawMaze(Maze maze, JFrame frame) {
        this.maze = maze;
        this.frame = frame;
        this.sizeX = INITIAL_WIDTH;
        this.sizeY = INITIAL_HEIGHT;

    }

    /**
     * Draws all components of the maze with their corresponding colour, and size calculated
     * from the size of maze and frame.
     *
     * @param g  the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Calculate the size of each rectangle based on size of maze and size of frame.
        int mazeY = this.maze.getMazeY();
        int mazeX = this.maze.getMazeX();
        Dimension size = this.frame.getSize();
        this.sizeY = (size.height - CLEARANCE) / mazeY;
        this.sizeX = (size.width - CLEARANCE) / mazeX;

        // Draw every component based on its colour from their classes, and the calculated size
        // from above.
        for (int y = 0; y < mazeY; y++) {
            for (int x = 0; x < mazeX; x++) {
                g.setColor(this.maze.getElement(x, y).getGuiColour());
                g.fillRect(OFFSET_X + (x * this.sizeX), OFFSET_Y + (y * this.sizeY),
                        this.sizeX, this.sizeY);
            }
        }
    }
}
