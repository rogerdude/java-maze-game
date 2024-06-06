package elements;

import java.awt.Color;

/**
 * A class that represents a traversable path in the maze.
 */
public class Path extends MazeComponent {
    /** The text colour for the path in unicode format */
    public static final String ANSI_BLACK = "\u001B[30m";

    @Override
    public String getTextColour() {
        return ANSI_BLACK;
    }

    @Override
    public Color getGuiColour() {
        return Color.WHITE;
    }
}
