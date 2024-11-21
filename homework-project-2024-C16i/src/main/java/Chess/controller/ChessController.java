package Chess.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.tinylog.Logger;

/**
 * The ChessController class handles the GUI logic for the chess game,
 * including user interactions and board updates
 */
public class ChessController {

    @FXML
    private GridPane board;

    private final ChessModel model = new ChessModel();
    private final ChessMoveSelector selector = new ChessMoveSelector(model);

    /**
     * Initialize the chessboard and set up the appearance and event handlers for each square.
     */
    @FXML
    private void initialize() {
        for (int i = 0; i < ChessModel.ROWS; i++) {
            for (int j = 0; j < ChessModel.COLS; j++) {
                StackPane square = createSquare(i, j);
                board.add(square, j, i);
                updateSquare(square, i, j);
            }
        }
    }
    /**
     * Creates a square on the chessboard at the specified row and column.
     *
     * @param row the row of the square
     * @param col the column of the square
     * @return the created StackPane representing the square
     */
    private StackPane createSquare(int row, int col) {
        StackPane pane = new StackPane();


        if ((row + col) % 2 == 0) {
            pane.setStyle("-fx-background-color: white;");
        } else {
            pane.setStyle("-fx-background-color: lightgray;");
        }

        pane.setOnMouseClicked(this::handleMouseClick);
        return pane;
    }
    /**
     * Updates the appearance of the square at the specified row and column.
     *
     * @param square the StackPane representing the square
     * @param row the row of the square
     * @param col the column of the square
     */
    private void updateSquare(StackPane square, int row, int col) {
        square.getChildren().clear();
        if (model.getSquare(new Position(row, col)) == Square.DARK_BISHOP) {
            Circle darkBishop = new Circle(20, Color.BLACK);
            square.getChildren().add(darkBishop);
        } else if (model.getSquare(new Position(row, col)) == Square.LIGHT_BISHOP) {
            Circle lightBishop = new Circle(20, Color.WHITE);
            lightBishop.setStroke(Color.BLACK);
            lightBishop.setStrokeWidth(2);
            square.getChildren().add(lightBishop);
        }
    }
    /**
     * Handles mouse click events on the squares of the chessboard.
     *
     * @param event the MouseEvent representing the click
     */
    @FXML
    private void handleMouseClick(MouseEvent event) {
        StackPane square = (StackPane) event.getSource();
        Integer row = GridPane.getRowIndex(square);
        Integer col = GridPane.getColumnIndex(square);

        if (row == null || col == null) {
            return;
        }

        Logger.info("Click on square ({},{})", row, col);
        Position clickedPosition = new Position(row, col);

        Position previousSelectedPosition = selector.getSelectedPosition();
        selector.select(clickedPosition);


        updateSquare(square, row, col);

        if (previousSelectedPosition != null && !previousSelectedPosition.equals(clickedPosition)) {
            StackPane previousSquare = getSquarePane(previousSelectedPosition.getRow(), previousSelectedPosition.getCol());
            updateSquare(previousSquare, previousSelectedPosition.getRow(), previousSelectedPosition.getCol());
        }
        checkWinCondition();
    }
    /**
     * Gets the StackPane representing the square at the specified row and column.
     *
     * @param row the row of the square
     * @param col the column of the square
     * @return the StackPane representing the square
     */
    private StackPane getSquarePane(int row, int col) {
        for (javafx.scene.Node node : board.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);
            if (rowIndex != null && colIndex != null && rowIndex == row && colIndex == col) {
                return (StackPane) node;
            }
        }
        return null;
    }
    /**
     * Checks the win condition for the game and displays an alert if the game is won.
     */
    private void checkWinCondition() {
        if (model.isSolved()) {
            Logger.info("Congratulations! All bishops have swapped places.");
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("Congratulations! All bishops have swapped places.");
            alert.showAndWait();
        }
    }
}


