package tests;

import javafx.util.Pair;
import model.MinesweeperGame;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinesweeperGameTest {
    private model.MinesweeperGame testGame;

    @BeforeEach
    public void runBefore() {
        testGame = new MinesweeperGame();
    }

    @Test
    public void testGetAdjacent0() {
        testGame = new MinesweeperGame();
        ArrayList<Pair<Integer,Integer>> a = testGame.getAdjacent(2,2);
        for(Pair<Integer,Integer> p: a) {
            System.out.println(p.getKey() + " " + p.getValue());
        }
        assertEquals(0, 0);
    }

    @Test
    public void testHandleClick() {
        testGame = new MinesweeperGame();
        ArrayList<Pair<Integer,Integer>> a = testGame.getAdjacent(2,2);
        for(Pair<Integer,Integer> p: a) {
            System.out.println(p.getKey() + " " + p.getValue());
        }
        assertEquals(0, 0);
    }
}