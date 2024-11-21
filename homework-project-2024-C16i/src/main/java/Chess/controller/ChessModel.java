package Chess.controller;

import puzzle.State;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;

/**
 * Represent the chess model for a simplified chess game.
 * Handle the board state and the rules for legal moves.
 */

public class ChessModel implements State {

    public static final int ROWS = 5;
    public static final int COLS = 4;
    private Move lastMove;
    private Square[][] board;

    /**
     * Initialize the chess model and set up the board.
     */
    public ChessModel() {
        board = new Square[ROWS][COLS];
        initializeBoard();
    }

    /**
     * Initialize the board with the bishops and with their starting positions.
     */
    private void initializeBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (i == 0) {
                    board[i][j] = Square.DARK_BISHOP;
                } else if (i == ROWS - 1) {
                    board[i][j] = Square.LIGHT_BISHOP;
                } else {
                    board[i][j] = Square.NONE;
                }
            }
        }
    }

    /**
     * Get the square at the specified position.
     * @param position the position on the board
     * @return the square at the specified position
     */
    public Square getSquare(Position position) {
        return board[position.getRow()][position.getCol()];
    }

    /**
     * Set the square at the specified position.
     * @param position the position on the board
     * @param square the square at the specified position
     */

    public void setSquare(Position position, Square square) {
        board[position.getRow()][position.getCol()] = square;
    }

    /**
     * Check if the is solved.
     * The game is solved when all dark bishops switched position with all light bishops.
     * @return true if the game is solved, otherwise false
     */
    @Override
    public boolean isSolved() {
        for (int j = 0; j < COLS; j++) {
            if (board[0][j] != Square.LIGHT_BISHOP || board[ROWS - 1][j] != Square.DARK_BISHOP) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the move is legal
     * @param o the move to check
     * @return true if the move is legal, otherwise false
     */
    @Override
    public boolean isLegalMove(Object o) {
        if (!(o instanceof Move)) {
            return false;
        }
        Move move = (Move) o;
        Position from = move.from();
        Position to = move.to();
        return isLegalMove(from, to);
    }

    /**
     * Make the move if it legal
     * @param o the move to make
     */
    @Override
    public void makeMove(Object o) {
        if (!(o instanceof Move)) {
            return;
        }
        Move move = (Move) o;
        Position from = move.from();
        Position to = move.to();
        if (isLegalMove(from, to)) {
            makeMove(from, to);
        }
    }

    /**
     * Get all legal moves
     * @return set of legal moves
     */
    @Override
    public Set getLegalMoves() {
        Set<Move> legalMoves = new HashSet<>();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Position from = new Position(row, col);
                if (isLegalToMoveFrom(from)) {
                    for (int toRow = 0; toRow < ROWS; toRow++) {
                        for (int toCol = 0; toCol < COLS; toCol++) {
                            Position to = new Position(toRow, toCol);
                            if (isLegalMove(from, to)) {
                                legalMoves.add(new Move(from, to));
                            }
                        }
                    }
                }
            }
        }
        return legalMoves;
    }

    /**
     * Create a clone of the current board state
     * @return the clone of the board state
     */
    @Override
    public State clone() {
        ChessModel clonedModel = new ChessModel();
        for (int row = 0; row < ROWS; row++) {
            System.arraycopy(board[row], 0, clonedModel.board[row], 0, COLS);
        }
        return clonedModel;
    }

    /**
     * Check is it legal to move from the  position
     * @param from the position to move from
     * @return true if is legal to move from the position, otherwise false
     */
    public boolean isLegalToMoveFrom(Position from) {
        return isOnBoard(from) && !isEmpty(from);
    }

    /**
     * Check the move from the position to the another is legal
     * @param from the position to move from
     * @param to the position to move to
     * @return true if the move is legal, otherwise false
     */
    public boolean isLegalMove(Position from, Position to) {
        if (!isLegalToMoveFrom(from) || !isOnBoard(to) || !isEmpty(to)) {
            return false;
        }


        int dx = Math.abs(to.getRow() - from.getRow());
        int dy = Math.abs(to.getCol() - from.getCol());
        if (dx != 1 || dy != 1) {
            return false;
        }


        Square fromSquare = getSquare(from);
        Square toSquare = getSquare(to);
        Square enemySquare = (fromSquare == Square.DARK_BISHOP) ? Square.LIGHT_BISHOP : Square.DARK_BISHOP;


        int[][] directions = { {1, 1}, {1, -1}, {-1, 1}, {-1, -1} };
        for (int[] direction : directions) {
            int row = to.getRow() + direction[0];
            int col = to.getCol() + direction[1];
            Position adjacent = new Position(row, col);
            if (isOnBoard(adjacent) && getSquare(adjacent) == enemySquare) {
                return false;
            }
        }

        return true;
    }


    /**
     * Make move from a position to another one if it is legal
     * @param from the position to move from
     * @param to  the position to move to
     */
    public void makeMove(Position from, Position to) {
        if (isLegalMove(from, to)) {
            board[to.getRow()][to.getCol()] = board[from.getRow()][from.getCol()];
            board[from.getRow()][from.getCol()] = Square.NONE;
            lastMove=new Move(from,to);
        }

    }

    /**
     * Check the specified position is empty
     * @param p the position to check
     * @return true if the position empty, otherwise false
     */
    public boolean isEmpty(Position p) {
        return board[p.getRow()][p.getCol()] == Square.NONE;
    }

    /**
     * Check the specified position is on the board
     * @param p the position to check
     * @return true if the position on the board, otherwise false
     */
    public static boolean isOnBoard(Position p) {
        return 0 <= p.getRow() && p.getRow() < ROWS && 0 <= p.getCol() && p.getCol() < COLS;
    }

    /**
     * Check the move is a valid bishop move
     * @param from the position to move from
     * @param to the position to move to
     * @return true if the move is a valid bishop move, otherwise false
     */
    public static boolean isBishopMove(Position from, Position to) {
        int dx = Math.abs(to.getRow() - from.getRow());
        int dy = Math.abs(to.getCol() - from.getCol());
        return dx == dy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessModel that = (ChessModel) o;
        return Objects.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Chess Model:\n");
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                sb.append(board[i][j]).append(" ");
            }
            sb.append("\n");
        }
        if (lastMove != null) {
            sb.append("Last move was from ")
                    .append(lastMove.from())
                    .append(" to ")
                    .append(lastMove.to())
                    .append("\n");
        }
        return sb.toString();
    }
}