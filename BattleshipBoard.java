import java.util.*;

public class BattleshipBoard {

    int[][] grid = new int[6][6];
    ArrayList<BattleshipShip> ships = new ArrayList<>();

    public void placeShipRandom(int length) {
        while (true) {
            int row = (int)(Math.random() * 6);
            int col = (int)(Math.random() * (6 - length));

            if (canPlace(row, col, length)) {
                BattleshipShip ship = new BattleshipShip(row, col, length);
                ships.add(ship);

                for (int i = 0; i < length; i++) {
                    grid[row][col + i] = 1;
                }
                break;
            }
        }
    }

    private boolean canPlace(int row, int col, int length) {
        for (int i = 0; i < length; i++) {
            if (grid[row][col + i] != 0) return false;
        }
        return true;
    }

    public boolean attack(int row, int col) {

        if (row < 0 || row >= 6 || col < 0 || col >= 6) {
            throw new IllegalArgumentException("Invalid coordinates");
        }

        if (grid[row][col] == 1) {
            grid[row][col] = 2;

            for (BattleshipShip s : ships) {
                if (s.contains(row, col)) {
                    s.hit();
                }
            }
            return true;

        } else if (grid[row][col] == 0) {
            grid[row][col] = 3;
            return false;
        }

        return false;
    }

    public int getShipsDestroyed() {
        int count = 0;
        for (BattleshipShip s : ships) {
            if (s.isDestroyed()) count++;
        }
        return count;
    }

    public boolean allShipsDestroyed() {
        return getShipsDestroyed() == ships.size();
    }
}