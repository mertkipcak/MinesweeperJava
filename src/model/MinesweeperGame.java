package model;


import javafx.util.Pair;

import java.util.*;

public class MinesweeperGame {

    public static final int BOARD_HEIGHT = 20;
    public static final int BOARD_WIDTH = 20;
    public static final int BOMB_COUNT = 25;

    public final Tile[][] board;
    public boolean flagMode = false;

    private final Random rand;


    /**
    * Initializes the game with a board sized BOARD_HEIGHT and BOARD_WIDTH.
    * Randomly places the bombs.
    * Then Prints the bomb locations and adjacent numbers to console (not visible to player)
    */
    public MinesweeperGame() {
        rand = new Random();
        board = new Tile[BOARD_WIDTH][BOARD_HEIGHT];
        for(int x = 0; x < BOARD_WIDTH; x++) {
            for(int y = 0; y < BOARD_HEIGHT; y++) {
                board[x][y] = new Tile(false);
            }
        }
        placeBombs();
        setAdjacent();
        printBoardBomb();
        printBoardAdjacent();
    }

    /**
     * Randomly places the bomb by picking a random number twice and checking if there is a bomb already there.
     * TODO: improve on this as it may get stuck or take a while with larger boards
     */
    public void placeBombs() {
        int i = BOMB_COUNT;
        while(i != 0) {
            int x = rand.nextInt(BOARD_WIDTH);
            int y = rand.nextInt(BOARD_HEIGHT);
            if (!board[x][y].bomb) {
                board[x][y].bomb = true;
                i--;
            }
        }
    }

    /**
     * Sets the adjcent numbers of each block (number of bombs in adjacent squares, max 8)
     */
    public void setAdjacent() {
        for(int x = 0; x < BOARD_WIDTH; x++) {
            for(int y = 0; y < BOARD_HEIGHT; y++) {
                ArrayList<Pair<Integer,Integer>> adjacentList = getAdjacent(x,y);
                Tile toChange = board[x][y];
                int count = 0;
                for(Pair<Integer,Integer> p: adjacentList) {
                    if (board[p.getKey()][p.getValue()].bomb) {
                        count++;
                    }
                }
                toChange.adjacent = count;
            }
        }
    }

    /**
     * toggles the flag value of the given tile
     * @param x: x value of the flag toggled tile
     * @param y: y value of the flag toggled tile
     */
    public void toggleFlag(int x, int y) {
        board[x][y].flagged = !board[x][y].flagged;
    }

    /**
     * toggles flag mode
     */
    public void toggleFlagMode() {
        flagMode = !flagMode;
    }

    /**
     * handles click on the tile (x,y)
     * @param x: x value of the clicked tile
     * @param y: y value of the clicked tile
     */
    public void handleClick(int x, int y) {
        Tile clicked = board[x][y];
        if(flagMode) {
            toggleFlag(x, y);
        } else {
            if(clicked.bomb) endGame();
            else {
                if(clicked.adjacent != 0) {
                    clicked.discovered = true;
                } else {
                    discoverNoAdjacent(x, y);
                }
            }
        }
    }

    /**
     * make a BFS iterating through all non-bomb tiles to discover that area starting from tile at (x,y)
     * @param x: x value of the start tile
     * @param y: y value of the start tile
     */
    public void discoverNoAdjacent(int x, int y) {
        Queue<Pair<Integer, Integer>> q = new LinkedList<>();
        Pair<Integer, Integer> curr = new Pair<>(x, y);
        q.add(curr);
        while(!q.isEmpty()) {
            curr = q.peek();
            q.remove();
            board[curr.getKey()][curr.getValue()].discovered = true;
            ArrayList<Pair<Integer,Integer>> adjacent = getAdjacent(curr.getKey(), curr.getValue());
            for(Pair<Integer,Integer> p: adjacent) {
                if (board[p.getKey()][p.getValue()].adjacent == 0 && !board[p.getKey()][p.getValue()].discovered) {
                    q.add(p);
                } else {
                    board[p.getKey()][p.getValue()].discovered = true;
                }
            }
        }
    }

    /**
     * Used to get the coordiantes of the adjacent tiles (up left, up, up right...)
     * does not add to adjacent if the adjacent coordinate is not in the board.
     * @param x: x value of the tile to get the adjacent blocks from
     * @param y: y value of the tile to get the adjacent blocks from
     * @return returns the list containing the adjacent tile coordinates
     */
    public ArrayList<Pair<Integer,Integer>> getAdjacent(int x, int y) {
        ArrayList<Pair<Integer,Integer>> temp = new ArrayList<>();
        ArrayList<Pair<Integer,Integer>> ret = new ArrayList<>();
        for(int a = x-1; a <= x+1; a++) {
            for(int b = y-1; b <= y+1; b++) {
                temp.add(new Pair<>(a,b));
            }
        }
        for(Pair<Integer,Integer> p: temp) {
            int a = p.getKey();
            int b = p.getValue();
            if(!(a < 0 || a >= BOARD_WIDTH || b < 0 || b >= BOARD_HEIGHT || (a == x && b == y))) {
                ret.add(p);
            }
        }
        return ret;
    }

    /**
     * Prints the bomb value of each value to the console
     * for developer control (and to cheat!)
     */
    private void printBoardBomb() {
        for(int x = 0; x < BOARD_WIDTH; x++) {
            for(int y = 0; y < BOARD_HEIGHT; y++) {
                System.out.printf("%b ", board[y][x].bomb);
            }
            System.out.print("\n");
        }
    }

    /**
     * Prints the adjacent value of each value to the console
     * for developer control (and to cheat!)
     */
    private void printBoardAdjacent() {
        for(int x = 0; x < BOARD_WIDTH; x++) {
            for(int y = 0; y < BOARD_HEIGHT; y++) {
                System.out.printf("%d ", board[y][x].adjacent);
            }
            System.out.print("\n");
        }
    }

    /**
     * Ends the game by discovering every block
     * TODO: improve this by showing a message?!
     */
    public void endGame() {
        for(int x = 0; x < BOARD_WIDTH; x++) {
            for(int y = 0; y < BOARD_HEIGHT; y++) {
                board[x][y].discovered = true;
            }
        }
    }
}
