public class TicTacToeBackend {
    private char[][] board; // 2D array to store game board (X, O, or empty)
    private char currentPlayer; // stores current player (X or O)

    public TicTacToeBackend() {
        board = new char[3][3];
        resetBoard(); // set initial state
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean makeMove(int r, int c) {
        if (board[r][c] == '\0') { // check if cell is empty
            board[r][c] = currentPlayer;
            return true;
        }
        return false; // move invalid (cell already filled)
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X'; // toggle between X and O
    }

    public boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            // check rows
            if (board[i][0] != '\0' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) return true;
            // check columns
            if (board[0][i] != '\0' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) return true;
        }
        // check main diagonal
        if (board[0][0] != '\0' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) return true;
        // check opposite diagonal
        if (board[0][2] != '\0' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) return true;

        return false;
    }

    public boolean isDraw() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '\0') return false;
        return true; // all cells filled and no winner which means draw
    }

    public void resetBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = '\0'; // clear all cells
        currentPlayer = 'X'; // game always starts with player X
    }
}
