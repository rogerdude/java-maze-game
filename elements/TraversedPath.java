package elements;

import java.awt.*;

/**
 * A class that represents a path that the player has travelled to on the maze.
 */
public class TraversedPath extends Path {
    /** The text colour for the travelled path in unicode format */
    public static final String ANSI_CYAN = "\u001B[36m";

    @Override
    public String getTextColour() {
        return ANSI_CYAN;
    }

    @Override
    public Color getGuiColour() {
        return Color.CYAN;
    }
}
