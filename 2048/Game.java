import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    // Main grid
    public static int[][] activeGrid = new int[4][4];
    // Scanner to get user input
    Scanner scanner;
    // Global boolean to check whether user has made a valid move
    boolean validMoveMade = true;
    // Keeps track of moves made
    int movesMade = 0;

    // Main function to start the game
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    // Handles creating a new game by initializing a scanner and creating the grid
    public Game() {
        initGrid();
        scanner = new Scanner(System.in);
    }

    // Handles the main function of the game
    public void start() {
        // Continue playing while the user has not lost and has not won
        while (!lost() && !won()) {
            // Print out the current grid
            printGrid(activeGrid);
            // Prompt user for input
            System.out.print("Make a move (w: up, a: left, s: down, d: right, r: restart, q: quit): ");
            // Get input from user
            String direction = scanner.nextLine();
            // Keep prompting user for move until they make a valid one
            do {
                // If valid move is not made, that means user has made an invalid move, so tell them that and prompt for another move
                if (!validMoveMade) {
                    System.out.print("Invalid move, try again: ");
                    direction = scanner.nextLine();
                }
                move(direction);
            } while (!validMoveMade);
            // Updates moves made
            movesMade++;
            // Prints buffer
            System.out.print("\n\n\n\n\n\n\n\n\n\n\n");
            // Lets user know move was valid
            System.out.println("You pressed " + direction + ". It was a valid move.");
            // Prints debugs
            printDebugs();
            // Generates new number for board
            generateNumber();
        }
        String input;
        // Force user to enter either y to restart or n to quit
        do {
            printGrid(activeGrid);
            // Let user know if they've won or lost
            if (won()) {
                System.out.printf("Congrats! You won in %d moves. Would you like to play again? (y/n): ", movesMade);
            }
            else {
                System.out.print("You have lost. Try again? (y/n): ");
            }
            input = scanner.nextLine();
        } while (!input.equals("y") && !input.equals("n"));
        // If input is y, restart
        if (input.equals("y")) {
            restart(false);
        }
        // If input is n, quit
        if (input.equals("n")) {
            quit(false);
        }
    }

    /**
     * Initializes the grid at the start of play by setting every location to 0 (blank)
     * And then generating 2 numbers at random points on the grid
     */
    public void initGrid() {
        // Resets validMoveMade
        validMoveMade = true;
        // Populates grid with blank spots
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                activeGrid[i][j] = 0;
            }
        }
        // Generates 2 random numbers
        generateNumber();
        generateNumber();
    }

    /**
     * Prints a 4x4 2D array to console in a grid format
     * @param grid an input of the 2D array to print
     */
    public void printGrid(int[][] grid) {
        // Top row
        System.out.println("—\t\t—\t\t—\t\t—\t\t—\t\t—");
        for (int[] ints : grid) {
            // Left column
            System.out.print("|\t\t");
            for (int j = 0; j < 4; j++) {
                // Prints blank (represented by 0 internally)
                if (ints[j] == 0) {
                    System.out.print("*\t\t");
                } else {
                    // Makes sure that if number is larger than 3 characters that spacing isn't messed up
                    if (ints[j] >= 1000) {
                        System.out.print(ints[j] + "\t");
                    }
                    else {
                        System.out.print(ints[j] + "\t\t");
                    }
                }
            }
            // Right column
            System.out.print("|");
            System.out.println();
        }
        // Bottom row
        System.out.println("—\t\t—\t\t—\t\t—\t\t—\t\t—");
    }

    /**
     * Generates a number at a random empty point on the grid
     */
    public boolean generateNumber() {
        int[] pos1;
        ArrayList<int[]> positions = new ArrayList<>();
        // Get all empty positions and add to positions list
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (activeGrid[i][j] == 0) {
                    positions.add(new int[]{i, j});
                }
            }
        }
        // Return false as there are no positions to place the number
        if (positions.size() == 0) {
            return false;
        }
        // Get random position from list of empty positions
        pos1 = positions.get((int) (Math.random() * positions.size()));
        // Get random value (20% chance of being 4, 80% chance of being 2)
        int value = Math.random() < 0.8 ? 2 : 4;
        // Place value in position
        activeGrid[pos1[0]][pos1[1]] = value;
        return true;
    }

    /**
     * Moves items on board to the right, left, up or down
     * Also handles input from the user for restarting and quitting
     * @param dir a string passed of what direction to move in
     */
    public void move(String dir) {
        // Keeps track of whether a move has been made during an iteration of one of the do-while loops
        // Without this boolean, errors can occur where a number might not move all the way in a direction
        boolean moveMadeDuringIteration;
        // Reset valid move made, as no valid moves have been made yet
        validMoveMade = false;
        System.out.println("You pressed " + dir);
        switch (dir) {
            // Up
            case "w" -> {
                // Loop through columns
                for (int j = 0; j < 4; j++) {
                    // Keep running movement til no more moves have been made to prevent "stuck number" bug
                    do {
                        // Reset moveMadeDuringIteration for new while loop iteration
                        moveMadeDuringIteration = false;
                        // Loop through all rows but the uppermost one (as it can't move any further up)
                        for (int i = 3; i > 0; i--) {
                            // If the currently selected position is not blank and has a valid move above it
                            if (activeGrid[i][j] != 0 && validMove("w", new int[]{i, j})) {
                                // A valid move has been made
                                validMoveMade = true;
                                // Move the current number up
                                activeGrid[i - 1][j] += activeGrid[i][j];
                                // Clear previous position as we have now moved up
                                activeGrid[i][j] = 0;
                                // A move has been made during this iteration
                                moveMadeDuringIteration = true;
                            }
                        }
                    } while (moveMadeDuringIteration);
                }
            }
            // Down
            case "s" -> {
                // Loop through columns
                for (int j = 0; j < 4; j++) {
                    do {
                        // Reset moveMadeDuringIteration for new while loop iteration
                        moveMadeDuringIteration = false;
                        // Loop through all rows but the bottommost one (as it can't move any further down)
                        for (int i = 0; i < 3; i++) {
                            // If the currently selected position is not blank and has a valid move below it
                            if (activeGrid[i][j] != 0 && validMove("s", new int[]{i, j})) {
                                // A valid move has been made
                                validMoveMade = true;
                                // Move the current number down
                                activeGrid[i + 1][j] += activeGrid[i][j];
                                // Clear previous position as we have now moved down
                                activeGrid[i][j] = 0;
                                moveMadeDuringIteration = true;
                            }
                        }
                    } while (moveMadeDuringIteration);
                }
            }
            // Left
            case "a" -> {
                // Loop through rows
                for (int i = 0; i < 4; i++) {
                    do {
                        // Reset moveMadeDuringIteration for new while loop iteration
                        moveMadeDuringIteration = false;
                        // Loop through all columns but the leftmost one (as it can't move any further left)
                        for (int j = 3; j > 0; j--) {
                            // If the currently selected position is not blank and has a valid move to the left of it
                            if (activeGrid[i][j] != 0 && validMove("a", new int[]{i, j})) {
                                // A valid move has been made
                                validMoveMade = true;
                                // Move the current number left
                                activeGrid[i][j - 1] += activeGrid[i][j];
                                // Clear previous position as we have now moved left
                                activeGrid[i][j] = 0;
                                moveMadeDuringIteration = true;
                            }
                        }
                    } while (moveMadeDuringIteration);
                }
            }
            // Right
            case "d" -> {
                // Loop through rows
                for (int i = 0; i < 4; i++) {
                    do {
                        // Reset moveMadeDuringIteration for new while loop iteration
                        moveMadeDuringIteration = false;
                        // Loop through all columns but the rightmost one (as it can't move any further right)
                        for (int j = 0; j < 3; j++) {
                            // If the currently selected position is not blank and has a valid move to the right of it
                            if (activeGrid[i][j] != 0 && validMove("d", new int[]{i, j})) {
                                // A valid move has been made
                                validMoveMade = true;
                                // Move the current number right
                                activeGrid[i][j + 1] += activeGrid[i][j];
                                // Clear previous position as we have now moved right
                                activeGrid[i][j] = 0;
                                moveMadeDuringIteration = true;
                            }
                        }
                    } while (moveMadeDuringIteration);
                }
            }
            // Restart
            case "r" -> restart(true);
            // Quit
            case "q" -> quit(true);
            // Invalid input
            default -> System.out.println("Invalid input.");
        }
    }

    /**
     * Restarts the game (sometimes with confirmation)
     * @param confirmation whether to confirm restart with user
     */
    public void restart(boolean confirmation) {
        if (confirmation) {
            System.out.print("To confirm restart, press r again. Otherwise, press any other key: ");
            if (!(scanner.nextLine().equals("r"))) {
                return;
            }
        }
        System.out.println("Restarting game.");
        initGrid();
        this.start();
    }

    /**
     * Quits the game (sometimes with confirmation)
     * @param confirmation whether to confirm quit with user
     */
    public void quit(boolean confirmation) {
        if (confirmation) {
            System.out.print("To confirm quit, press q again. Otherwise, press any other key: ");
            if (!(scanner.nextLine().equals("q"))) {
                return;
            }
        }
        System.out.println("Thanks for playing.");
        System.exit(1);
    }

    /**
     * Checks whether an item at position pos can move in direction dir
     * It can move if the item one over in direction dir contains a 0 or the same number at position pos
     * If not, the item cannot move
     * @param dir direction to check for valid move (w, a, s, d)
     * @param pos what item needs to be checked to see if it can move in dir
     * @return whether the item at position pos can be moved in direction dir
     */
    public boolean validMove(String dir, int[] pos) {
        // If the number is 0 there's no number to be moved
        if (activeGrid[pos[0]][pos[1]] == 0) {
            return false;
        }
        if (dir.equals("w")) {
            // If the number can't move further up
            if (pos[0] == 0) {
                return false;
            }
            // If the number above this number is the same number
            if (activeGrid[pos[0] - 1][pos[1]] == activeGrid[pos[0]][pos[1]]) {
                return true;
            }
            // If the number above this number is 0
            if (activeGrid[pos[0] - 1][pos[1]] == 0) {
                return true;
            }
            else {
                return false;
            }
        }
        if (dir.equals("s")) {
            // If the number can't move further down
            if (pos[0] == 3) {
                return false;
            }
            // If the number below this number is the same number
            if (activeGrid[pos[0] + 1][pos[1]] == activeGrid[pos[0]][pos[1]]) {
                return true;
            }
            // If the number below this number is 0
            if (activeGrid[pos[0] + 1][pos[1]] == 0) {
                return true;
            }
            else {
                return false;
            }
        }
        if (dir.equals("a")) {
            // If the number can't move further left
            if (pos[1] == 0) {
                return false;
            }
            // If the number to the left of this number is the same number
            if (activeGrid[pos[0]][pos[1] - 1] == activeGrid[pos[0]][pos[1]]) {
                return true;
            }
            // If the number above this number is 0
            if (activeGrid[pos[0]][pos[1] - 1] == 0) {
                return true;
            } else {
                return false;
            }
        }
        if (dir.equals("d")) {
            // If the number can't move further right
            if (pos[1] == 3) {
                return false;
            }
            // If the number to the right of this number is the same number
            if (activeGrid[pos[0]][pos[1] + 1] == activeGrid[pos[0]][pos[1]]) {
                return true;
            }
            // If the number to the right this number is 0
            if (activeGrid[pos[0]][pos[1] + 1] == 0) {
                return true;
            }
            else {
                return false;
            }
        }
        // If the function has reached this position, there must have been an invalid string passed, so tell them that
        System.out.println("Invalid string passed to validMove.");
        return false;
    }

    /**
     * Checks whether the user has lost
     * @return whether the user has lost (there are no more valid moves)
     */
    public boolean lost() {
        // If there is a valid move in any direction for any position return false, there are still valid moves
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (validMove("w", new int[]{i, j}) ||
                    validMove("a", new int[]{i, j}) ||
                    validMove("s", new int[]{i, j}) ||
                    validMove("d", new int[]{i, j})) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks whether the user has won
     * @return whether the user has won (2048 is on the board)
     */
    public boolean won() {
        // If 2048 exists return true
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (activeGrid[i][j] == 2048)
                    return true;
            }
        }
        return false;
    }

    // Prints maximum number on the board and the current number of moves
    public void printDebugs() {
        int max = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (activeGrid[i][j] > max) {
                    max = activeGrid[i][j];
                }
            }
        }
        System.out.printf("The maximum number on the board is %d and the current number of moves is %d\n", max, movesMade);
    }
}
