public class FourInARowBackend { 

    private final int ROWS = 6; // total rows
    private final int COLS = 7; // total columns

    private int[][] board; // game board (0 = empty, 1 = player1, 2 = player2)
    private int currentPlayer;

    public FourInARowBackend() {
        board = new int[ROWS][COLS]; // initialize board
        currentPlayer = 1; // player 1 starts
    }

    public int getCurrentPlayer() {
        return currentPlayer; // return current player
    }

    public int dropPiece(int col) throws Exception { // 🔥 added exception

        if (col < 0 || col >= COLS) { // check invalid column
            throw new Exception("Invalid column");
        }

        for (int row = ROWS - 1; row >= 0; row--) { // start from bottom
            if (board[row][col] == 0) {
                board[row][col] = currentPlayer;
                return row; // return placed row
            }
        }

        throw new Exception("Column full"); // 🔥 instead of -1
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1; // switch between player 1 and 2
    }

    public boolean checkWin(int row, int col) {
        int player = board[row][col]; // get current player at position

        return checkDirection(row, col, 1, 0, player) ||  // vertical
               checkDirection(row, col, 0, 1, player) ||  // horizontal
               checkDirection(row, col, 1, 1, player) ||  // diagonal \
               checkDirection(row, col, 1, -1, player);   // diagonal /
    }

    private boolean checkDirection(int r, int c, int dr, int dc, int player) {
        int count = 1; // count current piece

        count += countPieces(r, c, dr, dc, player); // forward direction
        count += countPieces(r, c, -dr, -dc, player); // backward direction

        return count >= 4;
    }

    private int countPieces(int r, int c, int dr, int dc, int player) {
        int count = 0;

        r += dr;
        c += dc;

        while (r >= 0 && r < ROWS && c >= 0 && c < COLS && board[r][c] == player) {
            count++; // count matching pieces
            r += dr;
            c += dc;
        }

        return count;
    }

    public void reset() {
        board = new int[ROWS][COLS]; // clear board
        currentPlayer = 1; // reset to player 1
    }
}