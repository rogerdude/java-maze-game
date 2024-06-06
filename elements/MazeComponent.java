package elements;

import java.awt.*;

/**
 * An interface for the components of the maze.
 * Components include:
 *      Wall: an obstacle for the player
 *      Path: a traversable path for player
 *      Position: location of player
 *      EndPoint: the location to find in maze.
 */
public abstract class MazeComponent {
    /** The x coordinate of the component */
    private int x;

    /** The y coordinate of the component */
    private int y;

    /**
     * Sets the x-coordinate of the component.
     *
     * @param x x-coordinate to set to.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the component.
     *
     * @param y y-coordinate to set to.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets the x-coordinate of the component.
     *
     * @return x-coordinate of component.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the component.
     *
     * @return y-coordinate of component.
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the text colour for the component in unicode format.
     *
     * @return text colour for the component in unicode format.
     */
    public abstract String getTextColour();

    /**
     * Gets the Color for the component for the GUI.
     *
     * @return an instance of Color object with component's GUI colour.
     */
    public abstract Color getGuiColour();
}
