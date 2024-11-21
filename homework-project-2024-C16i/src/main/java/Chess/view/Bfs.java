package Chess.view;
import Chess.controller.ChessModel;
import Chess.controller.ChessMoveSelector;
import puzzle.solver.BreadthFirstSearch;

public class Bfs {
    public static void main(String[] args) {
        var bfs=new BreadthFirstSearch<ChessMoveSelector>();
        bfs.solveAndPrintSolution(new ChessModel());
    }
}
