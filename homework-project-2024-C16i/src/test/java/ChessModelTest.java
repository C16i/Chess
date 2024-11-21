import Chess.controller.ChessModel;
import Chess.controller.Position;
import Chess.controller.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChessModelTest {
    private ChessModel model;

    @BeforeEach
    public void setUp() {
        model = new ChessModel();
    }

    @Test
    public void testInitializeBoard() {
        for (int col = 0; col < ChessModel.COLS; col++) {
            assertEquals(Square.DARK_BISHOP, model.getSquare(new Position(0, col)));
            assertEquals(Square.LIGHT_BISHOP, model.getSquare(new Position(ChessModel.ROWS - 1, col)));
            for (int row = 1; row < ChessModel.ROWS - 1; row++) {
                assertEquals(Square.NONE, model.getSquare(new Position(row, col)));
            }
        }
    }

    @Test
    public void testGetSquare() {
        assertEquals(Square.DARK_BISHOP, model.getSquare(new Position(0, 0)));
        assertEquals(Square.LIGHT_BISHOP, model.getSquare(new Position(ChessModel.ROWS - 1, 0)));
    }

    @Test
    public void testSetSquare() {
        Position position = new Position(2, 2);
        model.setSquare(position, Square.DARK_BISHOP);
        assertEquals(Square.DARK_BISHOP, model.getSquare(position));
    }

    @Test
    public void testIsSolved_initialState() {
        assertFalse(model.isSolved());
    }

    @Test
    public void testIsSolved_solvedState() {
        for (int col = 0; col < ChessModel.COLS; col++) {
            model.setSquare(new Position(0, col), Square.LIGHT_BISHOP);
            model.setSquare(new Position(ChessModel.ROWS - 1, col), Square.DARK_BISHOP);
        }
        assertTrue(model.isSolved());
    }

}
