package ui;

import model.MinesweeperGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static ui.GamePanel.BOX_SIDE;

public class MineSweeper extends JFrame {

    private final GamePanel gp;

    // constructs the frame that the game will be played on
    public MineSweeper() {
        super("MineSweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        gp = new GamePanel(new MinesweeperGame());
        add(gp);
        addMouseListener(new MouseHandler());
        addKeyListener(new KeyHandler());
        pack();
        centreOnScreen();
        setVisible(true);
    }

    /**
     * initializes the game
     */
    public static void main(String[] args) {
        new MineSweeper();
    }

    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    /**
     * mouse handler class:
     * every mouse action calls handleCLick function on the tile clicked
     */
    private class MouseHandler implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            gp.game.handleClick(e.getX() / BOX_SIDE, e.getY() / BOX_SIDE);
            gp.repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            gp.game.handleClick(e.getX() / BOX_SIDE, e.getY() / BOX_SIDE);
            gp.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            gp.game.handleClick(e.getX() / BOX_SIDE, e.getY() / BOX_SIDE);
            gp.repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * key handler that handles r for reset and f for flag mode
     */
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_R) {
                gp.game = new MinesweeperGame();
                gp.repaint();
            } else if (e.getKeyCode() == KeyEvent.VK_F){
                gp.game.toggleFlagMode();
                gp.repaint();
            }
        }
    }
}
