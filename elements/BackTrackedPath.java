package elements;

import java.awt.Color;

/**
 * A class that represents a path that the player has already travelled to on the maze.
 */
public class BackTrackedPath extends Path {
    /** The text colour for the backtracked path in unicode format */
    public static final String ANSI_BLUE = "\u001B[34m";

    @Override
    public String getTextColour() {
        return ANSI_BLUE;
    }

    @Override
    public Color getGuiColour() {
        return Color.BLUE;
    }
}
