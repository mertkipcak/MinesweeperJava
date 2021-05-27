package model;

// represents a tile in the game stored in the game itself
public class Tile {

    public boolean bomb;
    public boolean discovered;
    public int adjacent;
    public boolean flagged;

    /**
     * creates a new tile
     * @param bomb: determines if the tile contains a bomb
     */
    public Tile(boolean bomb) {
        this.bomb = bomb;
        discovered = false;
        adjacent = 0;
    }


}
