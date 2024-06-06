package elements;

import java.awt.Color;

/**
 * A class that represents the player's position in the maze.
 */
public class Position extends MazeComponent {
    /** The text colour for the player in unicode format */
    public static final String ANSI_GREEN = "\u001B[32m";

    @Override
    public String getTextColour() {
        return ANSI_GREEN;
    }

    @Override
    public Color getGuiColour() {
        return Color.GREEN;
    }
}
