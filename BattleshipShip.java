public class BattleshipShip {
    int row, col, length;
    int hits = 0;

    public BattleshipShip(int row, int col, int length) {
        this.row = row;
        this.col = col;
        this.length = length;
    }

    public boolean contains(int r, int c) {
        return r == row && c >= col && c < col + length;
    }

    public void hit() {
        hits++;
    }

    public boolean isDestroyed() {
        return hits == length;
    }
}