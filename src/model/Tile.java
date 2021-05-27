package model;

// represents a tile in the game stored in the game itself
public class Tile {

    public boolean bomb;
    public boolean discovered;
    public int adjacent;
    public boolean flagged;

    public Tile(boolean bomb) {
        this.bomb = bomb;
        discovered = false;
        adjacent = 0;
    }


}
