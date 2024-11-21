package Chess.controller;

public class Move {
    private final Position from;
    private final Position to;

    public Move(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    public Position from() {
        return from;
    }

    public Position to() {
        return to;
    }
}
