package Chess.controller;

import lombok.Getter;
import org.tinylog.Logger;

/**
 * The ChessMoveSelector class is responsible for the selection and the movement of
 * the pieces on the chessboard. It interacts with the ChessModel to perform the moves.
 */
public class ChessMoveSelector {

    @Getter
    private Position selectedPosition;
    private final ChessModel model;

    /**
     * Constructs a ChessMoveSelector with the specified model
     * @param model the chess model to interact with
     */
    public ChessMoveSelector(ChessModel model) {
        this.model = model;
    }

    /**
     * Selects a position on the board. If a position is already selected,
     * it attempts to move the piece from the selected position to the specified position.
     * @param position the position to select or move to
     */
    public void select(Position position) {
        if (selectedPosition == null) {
            if (model.isLegalToMoveFrom(position)) {
                selectedPosition = position;
                Logger.info("Selected position: ({}, {})", position.getRow(), position.getCol());
            }
        } else {
            if (model.isLegalMove(selectedPosition, position)) {
                model.makeMove(selectedPosition, position);
                Logger.info("Moved from ({}, {}) to ({}, {})", selectedPosition.getRow(), selectedPosition.getCol(), position.getRow(), position.getCol());
            }
            selectedPosition = null;
        }
    }


}
