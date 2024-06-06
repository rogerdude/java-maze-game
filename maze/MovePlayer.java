package maze;

/**
 * An interface to implement lambda expressions for up, down, left, and right movements
 */
public interface MovePlayer {
    /**
     * Moves player to the specified coordinates.
     *
     * @param x the destination x-coordinate
     * @param y the destination y-coordinate
     */
    void move(int x, int y);
}
