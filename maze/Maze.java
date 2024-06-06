package maze;

import elements.*;

/**
 * A class that represents the maze to navigate, containing MazeComponents
 * and a 2D array of the maze component coordinates.
 */
public class Maze {
    /** The length of the maze in y-axis */
    private final int mazeY;

    /** The length of te maze in x-axis */
    private final int mazeX;

    /** A 2D array of all maze components */
    private final MazeComponent[][] maze;

    /** The x coordinate of the player */
    private int playerX;

    /** The y coordinate of the player */
    private int playerY;

    /** The x coordinate of the end point */
    private int endX;

    /** The y coordinate of the end point */
    private int endY;

    /** The x coordinate of the start point */
    private int startX;

    /** The y coordinate of the end point */
    private int startY;

    /**
     * The constructor for the Maze object which initialises the 2D array of maze components.
     *
     * @param mazeData a 2D array of the maze components from io.FileLoader.load()
     */
    public Maze(char[][] mazeData) {
        this.mazeY = mazeData.length;
        this.mazeX = mazeData[0].length;
        this.maze = new MazeComponent[mazeY][mazeX];

        // Iterate through mazeData to create an object instance of each element to put into
        // the 2D maze component array.
        for (int y = 0; y < this.mazeY; y++) {
            for (int x = 0; x < this.mazeX; x++) {
                this.maze[y][x] = findElement(mazeData[y][x], x, y);
            }
        }
    }

    /**
     * Makes a new instance of MazeComponent based on 'element' to put into the 2D array of
     * maze components.
     */
    private MazeComponent findElement(char element, int x, int y) {
        MazeComponent mazeElement;
        switch (element) {
            case '#' -> mazeElement = new Wall();
            case 'S' -> {
                mazeElement = new Position();
                this.playerX = x;
                this.playerY = y;
                this.startX = x;
                this.startY = y;
            }
            case 'E' -> {
                mazeElement = new EndPoint();
                this.endX = x;
                this.endY = y;
            }
            case ' ', '.' -> mazeElement = new Path();
            default -> mazeElement = null;
        }
        return mazeElement;
    }

    /**
     * Sets the player coordinates in the 2D maze component array, and checks if it is a
     * valid place for component to be positioned.
     *
     * @param x x-coordinate to move player to.
     * @param y y-coordinate to move player to.
     */
    public void setPlayerCoordinates(int x, int y) {
        // Check if x and y are within maze length
        if (x < 0 || x > this.mazeX || y < 0 || y > this.mazeY) {
            return;
        }

        // Move player to coordinate and replace the previous path with TraversedPath
        if (this.maze[y][x] instanceof Path || this.maze[y][x] instanceof EndPoint) {
            maze[this.playerY][this.playerX] = new TraversedPath();
            this.playerX = x;
            this.playerY = y;
            maze[this.playerY][this.playerX] = new Position();
        }
    }

    /**
     * Gets the length of the maze in x-axis.
     *
     * @return length of maze in x-axis
     */
    public int getMazeX() {
        return this.mazeX;
    }

    /**
     * Gets the length of maze in y-axis
     *
     * @return length of maze in y-axis
     */
    public int getMazeY() {
        return this.mazeY;
    }

    /**
     * Gets the x coordinate of the player on the maze.
     *
     * @return x coordinate of player
     */
    public int getPlayerX() {
        return this.playerX;
    }

    /**
     * Gets the y coordinate of the player on the maze.
     *
     * @return y coordinate of player
     */
    public int getPlayerY() {
        return this.playerY;
    }

    /**
     * Gets the x coordinate of the start point.
     *
     * @return x coordinate of start point.
     */
    public int getStartX() {
        return startX;
    }

    /**
     * Gets the y coordinate of the start point.
     *
     * @return y coordinate of start point
     */
    public int getStartY() {
        return startY;
    }

    /**
     * Moves the player through an already traversed path, and replaces the path
     * with an instance of BackTrackedPath.
     *
     * @param x x-coordinate to move player to.
     * @param y y-coordinate to move player to.
     */
    public void setPlayerBackTrack(int x, int y) {
        maze[this.playerY][this.playerX] = new BackTrackedPath();
        this.playerX = x;
        this.playerY = y;
        maze[this.playerY][this.playerX] = new Position();
    }

    /**
     * Gets the instance of MazeComponent from the specified coordinates in
     * 2D array of maze components.
     *
     * @param x x-coordinate of component
     * @param y y-coordinate of component
     * @return the instance of MazeComponent from specified coordinates from 2D array
     */
    public MazeComponent getElement(int x, int y) {
        return maze[y][x];
    }

    /**
     * Determines whether the player has reached the end point.
     *
     * @return true if player has reached end point, else return false.
     */
    public boolean isGameOver() {
        return this.playerX == endX && this.playerY == endY;
    }

}
