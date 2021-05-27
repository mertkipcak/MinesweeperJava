package ui;

import model.MinesweeperGame;

import javax.swing.*;
import java.awt.*;

import static model.MinesweeperGame.BOARD_HEIGHT;
import static model.MinesweeperGame.BOARD_WIDTH;

public class GamePanel extends JPanel {

    public static final int BOX_SIDE = 30;
    private static final Color UNDISCOVERED = new Color(120, 120, 120);
    private static final Color BOMB = new Color(26, 26, 26);
    private static final Color DISCOVERED = new Color(92, 92, 92);
    private static final Color FLAG = new Color(187, 5, 5);

    public MinesweeperGame game;

    // Constructs a game panel
    // EFFECTS: sets size and background colour of panel
    //          updates this with the game to be displayed
    //          also imports images
    public GamePanel(MinesweeperGame g) {
        setPreferredSize(new Dimension(BOARD_WIDTH * BOX_SIDE,
                BOARD_HEIGHT * BOX_SIDE));
        this.game = g;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGame(g);
    }

    private void drawGame(Graphics g) {
        for(int x = 0; x < BOARD_WIDTH; x++) {
            for(int y = 0; y < BOARD_HEIGHT; y++) {
                drawSquare(g, x, y);
            }
        }
        drawGrid(g);
    }

    private void drawGrid(Graphics g) {
        for(int x = 0; x < BOARD_WIDTH; x++) {
            for(int y = 0; y < BOARD_HEIGHT; y++) {
                drawRectangle(g, x, y);
            }
        }
    }

    private void drawRectangle(Graphics g, int x, int y) {
        Color savedCol = g.getColor();
        if(game.flagMode) {
            g.setColor(Color.darkGray);
        } else {
            g.setColor(Color.black);
        }
        g.drawRect(x* BOX_SIDE, y* BOX_SIDE, BOX_SIDE -1, BOX_SIDE -1 );
        g.setColor(savedCol);
    }

    private void drawSquare(Graphics g, int x, int y) {
        Color savedCol = g.getColor();
        if(!game.board[x][y].discovered) {
            if(game.board[x][y].flagged) {
                g.setColor(FLAG);
            } else {
                g.setColor(UNDISCOVERED);
            }
        } else {
            if(game.board[x][y].bomb) {
                g.setColor(BOMB);
            } else {
                g.setColor(DISCOVERED);
            }
        }
        g.fillRect(x * BOX_SIDE, y * BOX_SIDE, BOX_SIDE, BOX_SIDE);
        if(!game.board[x][y].bomb && game.board[x][y].discovered && game.board[x][y].adjacent != 0) {
            switch (game.board[x][y].adjacent) {
                case 1:
                    g.setColor(Color.blue);
                    break;
                case 2:
                    g.setColor(Color.green);
                    break;
                case 3:
                    g.setColor(Color.red);
                    break;
                case 4:
                    g.setColor(Color.yellow);
                    break;
                case 5:
                    g.setColor(Color.magenta);
                    break;
                case 6:
                    g.setColor(Color.cyan);
                    break;
                case 7:
                    g.setColor(Color.white);
                    break;
                case 8:
                    g.setColor(Color.black);
                    break;
            }
            g.drawString(Integer.toString(game.board[x][y].adjacent), x * BOX_SIDE + BOX_SIDE / 3, y * BOX_SIDE + 2
                    * BOX_SIDE / 3);
        }
        g.setColor(savedCol);
    }
}
