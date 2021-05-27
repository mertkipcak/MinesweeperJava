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

    public void toggleFlag(int x, int y) {
        board[x][y].flagged = !board[x][y].flagged;
    }

    public void toggleFlagMode() {
        flagMode = !flagMode;
    }

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

    private void printBoardBomb() {
        for(int x = 0; x < BOARD_WIDTH; x++) {
            for(int y = 0; y < BOARD_HEIGHT; y++) {
                System.out.printf("%b ", board[y][x].bomb);
            }
            System.out.print("\n");
        }
    }

    private void printBoardAdjacent() {
        for(int x = 0; x < BOARD_WIDTH; x++) {
            for(int y = 0; y < BOARD_HEIGHT; y++) {
                System.out.printf("%d ", board[y][x].adjacent);
            }
            System.out.print("\n");
        }
    }

    public void endGame() {
        for(int x = 0; x < BOARD_WIDTH; x++) {
            for(int y = 0; y < BOARD_HEIGHT; y++) {
                board[x][y].discovered = true;
            }
        }
    }
}
