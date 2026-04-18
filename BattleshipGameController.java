public class BattleshipGameController {

    BattleshipBoard player1Board = new BattleshipBoard();
    BattleshipBoard player2Board = new BattleshipBoard();

    boolean player1Turn = true;

    public BattleshipGameController() {
        int[] ships = {4, 3, 2};

        for (int s : ships) {
            player1Board.placeShipRandom(s);
            player2Board.placeShipRandom(s);
        }
    }

    public boolean attack(int row, int col) {
        if (player1Turn) {
            return player2Board.attack(row, col);
        } else {
            return player1Board.attack(row, col);
        }
    }

    public String getTurnText() {
        return player1Turn ? "Player 1 Turn" : "Player 2 Turn";
    }

    public int getP1Destroyed() {
        return player2Board.getShipsDestroyed();
    }

    public int getP2Destroyed() {
        return player1Board.getShipsDestroyed();
    }

    public boolean isGameOver() {
        return player1Board.allShipsDestroyed() || player2Board.allShipsDestroyed();
    }
}