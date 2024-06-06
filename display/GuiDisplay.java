package display;

import exceptions.MazeMalformedException;
import exceptions.MazeSizeMissmatchException;
import io.FileLoader;
import maze.Maze;
import maze.MovePlayer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * A class that displays the maze in a GUI and navigates it with user input.
 */
public class GuiDisplay implements KeyListener, ActionListener {
    /** The initial height of the main GUI frame in pixels */
    public static final int INITIAL_HEIGHT = 730;

    /** The initial width of the main GUI frame in pixels */
    public static final int INITIAL_WIDTH = 730;

    /** The height of the message box in pixels */
    public static final int MSG_BOX_HEIGHT = 100;

    /** The width of the message box in pixels */
    public static final int MSG_BOX_WIDTH = 450;

    /** The name of the main GUI frame */
    public static final String FRAME_NAME = "Maze Navigator";

    /** The name for the new maze button */
    public static final String NEW_MAZE = "New Maze";

    /** The name for the add file button */
    public static final String ADD_FILE = "Add file";

    /** The title of the message box dialog box */
    public static final String EXIT_MSG = "Exit was found!";

    /** Text to display between new maze button and drop down menu */
    public static final String CHOOSE_MAZE = "Choose maze map: ";

    /** Filepath for one of the default maze files to show in dropdown menu */
    public static final String SAMPLE_ONE = "Small.txt";

    /** Filepath for one of the default maze files to show in dropdown menu */
    public static final String SAMPLE_TWO = "Medium.txt";

    /** Filepath for one of the default maze files to show in dropdown menu */
    public static final String SAMPLE_THREE = "Large.txt";

    /** The Maze object containing the 2D array of MazeComponent to navigate */
    private Maze maze;

    /** The main frame to open the GUI in */
    private JFrame frame;

    /** The "New Game" button to start a new maze */
    private JButton newGame;

    /** The dropdown menu to choose maze files */
    private JComboBox<String> dropDown;

    /** An array of the file paths of the maze files in the dropdown menu */
    private ArrayList<String> mazeFilePath;

    /** The panel containing all menu bar and the drawn maze */
    private GuiDrawMaze mazeFrame;

    /** The menu bar at top of GUI to start new maze and choose different file */
    private JPanel menu;

    /** The height of the frame */
    private int height;

    /** The width of the frame */
    private int width;

    /** A boolean indicating whether the message box is open */
    private boolean msgBoxOpen = false;

    /** The dropdown menu to choose maze files in the message box */
    private JComboBox<String> msgBoxDropDown;

    /** A button for the file chooser */
    private JButton addFile;

    /** An array of the file names to show in the dropdown menu */
    private ArrayList<String> fileNames;

    /**
     * Initialises and displays the GUI, either with a file or without.
     *
     * @param maze an instance of Maze containing all maze data and components
     * @param filename name of the maze file, but it can also be null.
     */
    public GuiDisplay(Maze maze, String filename) {
        this.maze = maze;

        // Initialise main frame
        this.width = INITIAL_WIDTH;
        this.height = INITIAL_HEIGHT;
        this.frame = new JFrame(FRAME_NAME);

        // Initialise panel to draw maze
        this.mazeFrame = new GuiDrawMaze(this.maze, this.frame);

        // Initialise arrays to store filepaths and filenames
        this.mazeFilePath = new ArrayList<>();
        this.fileNames = new ArrayList<>();
        if (filename != null) {
            this.mazeFilePath.add(filename);
        }
        addDefaultFiles();

        initGui();
    }

    /**
     * Adds the default sample files to the array of filenames and filepaths.
     */
    private void addDefaultFiles() {
        this.mazeFilePath.add(SAMPLE_ONE);
        this.mazeFilePath.add(SAMPLE_TWO);
        this.mazeFilePath.add(SAMPLE_THREE);

        for (String fileName : this.mazeFilePath) {
            File file = new File(fileName);
            this.fileNames.add(file.getName());
        }
    }

    /**
     * Sets up the GUI's main frame and menu panel.
     */
    private void initGui() {
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.menu = new JPanel();

        this.newGame = createNewGameButton();
        this.menu.add(this.newGame);

        drawMenuText(this.menu);

        this.dropDown = createDropDown();
        this.menu.add(this.dropDown);

        this.addFile = createAddFileButton();
        this.menu.add(this.addFile);

        this.mazeFrame.add(menu);
        this.frame.add(this.mazeFrame);
        this.frame.pack();

        this.frame.setSize(this.width, this.height);
        this.frame.setFocusable(true);
        this.frame.setVisible(true);

        this.frame.addKeyListener(this);
        this.newGame.setFocusable(false);
        this.dropDown.setFocusable(false);
    }

    /**
     * Makes a JLabel with text to put in between the new game button and dropdown menu
     */
    private void drawMenuText(JPanel menu) {
        JLabel gap = new JLabel("    ");
        menu.add(gap);

        JLabel fileText = new JLabel(CHOOSE_MAZE);
        menu.add(fileText);
    }

    /**
     * Makes a button for the menu panel and message box
     */
    private JButton createNewGameButton() {
        JButton newGame = new JButton(NEW_MAZE);
        newGame.addActionListener(this);
        return newGame;
    }

    /**
     * Makes a dropdown menu with all filenames for menu panel and message box
     */
    private JComboBox<String> createDropDown() {
        JComboBox<String> dropDown = new JComboBox<>();
        for (String fileName : this.fileNames) {
            dropDown.addItem(fileName);
        }
        dropDown.setSelectedIndex(0);
        return dropDown;
    }

    /**
     * Makes a button for the file chooser to add files to drop down menu
     */
    private JButton createAddFileButton() {
        JButton addFile = new JButton(ADD_FILE);
        addFile.addActionListener(this);
        return addFile;
    }

    /**
     * Makes a message box for when the exit is found to choose next maze file.
     */
    private void createWinMsgBox() {
        JDialog msg = new JDialog();
        msg.setTitle(EXIT_MSG);
        msg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel msgPanel = new JPanel();

        msgPanel.add(createNewGameButton());
        drawMenuText(msgPanel);

        this.msgBoxDropDown = createDropDown();
        this.msgBoxDropDown.setSelectedIndex(this.dropDown.getSelectedIndex());
        msgPanel.add(this.msgBoxDropDown);

        msgPanel.add(createAddFileButton());

        msg.add(msgPanel);
        msg.pack();

        msg.setSize(MSG_BOX_WIDTH, MSG_BOX_HEIGHT);
        msg.setVisible(true);
        this.msgBoxOpen = true;
    }

    /**
     * Move's the player's position in the maze based on user input, and updates maze display
     */
    private void userMove(KeyEvent key) {
        // Initialise lambda expressions for up, down, left, and right
        MovePlayer up = (int x, int y) -> this.maze.setPlayerCoordinates(x, y - 1);
        MovePlayer down = (int x, int y) -> this.maze.setPlayerCoordinates(x, y + 1);
        MovePlayer right = (int x, int y) -> this.maze.setPlayerCoordinates(x + 1, y);
        MovePlayer left = (int x, int y) -> this.maze.setPlayerCoordinates(x - 1, y);

        int playerX = this.maze.getPlayerX();
        int playerY = this.maze.getPlayerY();

        switch (Character.toUpperCase(key.getKeyChar())) {
            case 'W' -> {
                up.move(playerX, playerY);
                this.mazeFrame.repaint();
            }
            case 'S' -> {
                down.move(playerX, playerY);
                this.mazeFrame.repaint();
            }
            case 'A' -> {
                left.move(playerX, playerY);
                this.mazeFrame.repaint();
            }
            case 'D' -> {
                right.move(playerX, playerY);
                this.mazeFrame.repaint();
            }
            default -> {
            }
        }
    }

    /**
     * Executes the function for the corresponding button.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case NEW_MAZE -> setNewGame();
            case ADD_FILE -> addToMazeFilePath();
        }
    }

    /**
     * Starts a new maze with the selected maze file in the dropdown menu for when
     * the new game button is pressed.
     */
    private void setNewGame() {
        this.newGame.setFocusable(false);
        this.dropDown.setFocusable(false);

        // Get the filename from drop down menu, either in main frame or message box
        int selectedIndex;
        if (this.msgBoxOpen) {
            selectedIndex = msgBoxDropDown.getSelectedIndex();
        } else {
            selectedIndex = this.dropDown.getSelectedIndex();
        }
        String fileName = this.mazeFilePath.get(selectedIndex);

        this.msgBoxOpen = false;

        try {
            // Remove frame components before re-initialising it
            this.frame.remove(this.menu);
            this.frame.remove(this.mazeFrame);
            this.frame.removeKeyListener(this);

            this.maze = new Maze(new FileLoader().load(fileName));
            this.mazeFrame = new GuiDrawMaze(this.maze, this.frame);

            initGui();
            this.dropDown.setSelectedIndex(selectedIndex);

        } catch (MazeMalformedException | MazeSizeMissmatchException | FileNotFoundException ex) {
            // A RuntimeException must be thrown for these exceptions when new game button is
            // pressed because 'actionPerformed' function cannot throw custom exceptions.
            throw new RuntimeException(ex);
        }
    }

    /**
     * Opens a file chooser and adds the selected file to the dropdown menu.
     */
    private void addToMazeFilePath() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this.menu);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            /* Get the filepath of the selected file to put into the corresponding array.*/
            this.mazeFilePath.add(fileChooser.getSelectedFile().getPath());
            /* Get the name of the file to put into the file names array */
            this.fileNames.add(fileChooser.getName(fileChooser.getSelectedFile()));

            // Add file names to drop down menu.
            this.dropDown.addItem(fileChooser.getName(fileChooser.getSelectedFile()));
            this.dropDown.setSelectedIndex(this.dropDown.getItemCount() - 1);
        }
        this.addFile.setFocusable(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Executes user move when a key is pressed and checks if user has found exit.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        userMove(e);
        if (this.maze.isGameOver()) {
            this.frame.setFocusable(false);
            createWinMsgBox();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
