package elements;

import java.awt.Color;

/**
 * A class that represents an obstacle in the maze.
 */
public class Wall extends MazeComponent {
    /** The text colour for the wall in unicode format */
    public static final String ANSI_GRAY = "\u001B[90m";

    @Override
    public String getTextColour() {
        return ANSI_GRAY;
    }

    @Override
    public Color getGuiColour() {
        return Color.GRAY;
    }
}
