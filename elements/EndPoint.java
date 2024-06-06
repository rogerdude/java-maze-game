package elements;

import java.awt.Color;

/**
 * A class that represents the end point in the maze.
 */
public class EndPoint extends MazeComponent {
    /** The text colour for the end point in unicode format */
    public static final String ANSI_RED = "\u001B[31m";

    @Override
    public String getTextColour() {
        return ANSI_RED;
    }

    @Override
    public Color getGuiColour() {
        return Color.RED;
    }
}
