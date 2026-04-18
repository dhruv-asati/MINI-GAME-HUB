public class TicTacToeBackend {
    private char[][] board;
    private char currentPlayer;

    public TicTacToeBackend() {
        board = new char[3][3];
        resetBoard();
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean makeMove(int r, int c) {
        if (board[r][c] == '\0') {
            board[r][c] = currentPlayer;
            return true;
        }
        return false;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != '\0' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) return true;
            if (board[0][i] != '\0' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) return true;
        }

        if (board[0][0] != '\0' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) return true;
        if (board[0][2] != '\0' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) return true;

        return false;
    }

    public boolean isDraw() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '\0') return false;

        return true;
    }

    public void resetBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = '\0';

        currentPlayer = 'X';
    }

    // 🔥 REQUIRED FIX
    public char[][] getBoard() {
        return board;
    }
}