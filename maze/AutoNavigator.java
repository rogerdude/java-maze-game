package maze;

import display.TextDisplay;
import elements.*;
import java.util.LinkedList;

/**
 * A class that uses an algorithm to automatically navigate the maze in the text display.
 */
public class AutoNavigator {
    /** The delay between each move */
    public static final int DELAY = 500;

    /** Index of up for moves array */
    public static final int UP = 0;

    /** Index of down for moves array */
    public static final int DOWN = 1;

    /** Index of right for moves array */
    public static final int RIGHT = 2;

    /** Index of left for moves array */
    public static final int LEFT = 3;

    /** Total number of elements in moves array */
    public static final int NUM_OF_MOVES = 4;

    /** An array of Path objects of the path the auto-player takes */
    private LinkedList<MazeComponent> pathTaken;

    /** The Maze object containing the 2D array of MazeComponent to navigate and display */
    private Maze maze;

    /** The instance of TextDisplay to display to. */
    private TextDisplay display;

    /**
     * Constructor which initialises an instance for the maze, display, and path list.
     *
     * @param maze an instance of Maze containing all maze data and components
     * @param display an instance of TextDisplay to display maze to.
     */
    public AutoNavigator(Maze maze, TextDisplay display) {
        this.maze = maze;
        this.display = display;
        this.pathTaken = new LinkedList<>();
    }

    /**
     * Navigates the maze using recursion to do a deep check of every path in the maze, and
     * it displays the maze as the algorithm moves through it.
     *
     * @throws InterruptedException if Thread.sleep() is interrupted while it is sleeping.
     */
    public void navigate() throws InterruptedException {
        // Make an array of coordinates of after player has moved in any direction. These
        // coordinates point to possible paths for player to travel.
        int x = this.maze.getPlayerX();
        int y = this.maze.getPlayerY();
        int[][] moves = moveCoordinates(x, y);

        // Iterate through each possible move based on moves array.
        for (int move = 0; move < NUM_OF_MOVES; move++) {
            if (this.maze.isGameOver()) {
                return;
            }

            // Get coordinates of player after it has "moved".
            int newX = moves[move][0];
            int newY = moves[move][1];

            // Check if coordinates are valid
            if (newX < 0 || newX >= this.maze.getMazeX()
                    || newY < 0 || newY >= this.maze.getMazeY()) {
                continue;
            }

            // Check if there is wall or an already travelled path at coordinate
            if (!(this.maze.getElement(newX, newY) instanceof Wall)
                    && !(this.maze.getElement(newX, newY) instanceof TraversedPath)) {

                // Store coordinate of path into its own object for later reference.
                this.maze.getElement(newX, newY).setX(newX);
                this.maze.getElement(newX, newY).setY(newY);

                // Backtrack the path if auto-player has jumped back to the forked path.
                checkJump(newX, newY, move);

                // Add the current position to the path list for future reference.
                this.pathTaken.add(this.maze.getElement(newX, newY));

                // Move auto-player to valid path and display it.
                travelPath(this.maze.getElement(newX, newY));
                this.display.displayMaze();
                Thread.sleep(DELAY);

                // Check next path
                navigate();
            }
        }
    }

    /**
     * Make an array of the coordinates for after player has made a move. This is to scope
     * all the possible paths at current position of player.
     */
    private int[][] moveCoordinates(int x, int y) {
        int[][] moves = new int[NUM_OF_MOVES][];
        moves[UP] = new int[]{x, y - 1};
        moves[DOWN] = new int[]{x, y + 1};
        moves[RIGHT] = new int[]{x + 1, y};
        moves[LEFT] = new int[]{x - 1, y};
        return moves;
    }

    /**
     * Checks if auto-player has jumped back to where the path forks. If it has, then show
     * the player backtracking to where the forked path is.
     */
    private void checkJump(int newX, int newY, int direction) throws InterruptedException {
        if (!this.pathTaken.isEmpty()) {
            // Make an array of the moves the player could have made at the path before a
            // possible jump.
            int oldX = this.pathTaken.getLast().getX();
            int oldY = this.pathTaken.getLast().getY();
            int[][] cmpMoves = moveCoordinates(oldX, oldY);

            // Compare the current coordinates of player with the previous coordinates
            // to determine whether player jumped.
            boolean valid = false;
            for (int j = 0; j < NUM_OF_MOVES; j++) {
                if (newX == cmpMoves[j][0] && newY == cmpMoves[j][1]) {
                    valid = true;
                    break;
                }
            }

            // Display the player backtracking if it has jumped.
            if (!valid) {
                backTrack(newX, newY, direction);
            }
        }
    }

    /**
     * Displays the player backtracking to the coordinates of the path where it forks
     */
    private void backTrack(int x, int y, int direction) throws InterruptedException {
        // Determine the coordinates of the forked path based on the coordinates after jump
        // and the direction the player last moved it.
        int forkX = x;
        int forkY = y;
        /* The forked path will be opposite to the direction where auto-player currently is. */
        switch (direction) {
            case UP -> forkY += 1;
            case DOWN -> forkY -= 1;
            case RIGHT -> forkX -= 1;
            case LEFT -> forkX += 1;
        }

        // Iterate through the path list to display the player backtracking and remove
        // the backtracked path until it reaches the forked path.
        this.pathTaken.removeLast();
        int size = this.pathTaken.size();
        for (int i = size - 1; i >= 0; i--) {
            travelPlayerBack(this.pathTaken.getLast());
            this.display.displayMaze();
            Thread.sleep(DELAY);
            if (this.pathTaken.getLast().getX() == forkX
                    && this.pathTaken.getLast().getY() == forkY) {
                break;
            }
            this.pathTaken.removeLast();
        }
    }

    /**
     * Moves the auto-player along the path and replaces the traversed path
     * with an instance of TraversedPath.
     */
    private void travelPath(MazeComponent path) {
        this.maze.setPlayerCoordinates(path.getX(), path.getY());
    }

    /**
     * Moves the auto-player along the backtracked path and replaces path with
     * an instance of BackTrackedPath.
     */
    private void travelPlayerBack(MazeComponent path) {
        this.maze.setPlayerBackTrack(path.getX(), path.getY());
    }
}
