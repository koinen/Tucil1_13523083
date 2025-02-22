public class Game {
    public long runtime;
    public long iterations;
    public boolean solved;
    public Board board;
    public Piece[] pieces;
    public boolean invalid;
    private boolean deadend;
    public Game(Piece[] p, Board b) {
        board = b;
        pieces = p;
        // Start the timer (in milliseconds)
    }
    public Game() {
        invalid = true;
    }
    public void solve() {
        iterations = 0;
        long startTime = System.currentTimeMillis();
        solveRecurse(0, 0, 0);
        long endTime = System.currentTimeMillis();
        runtime = endTime - startTime;
    }
    private void solveRecurse(int idx, int r, int c) {
        if (solved || deadend) {
            return;
        }
        if (r + pieces[idx].getRow() - 1 < board.getRow()) {
            if (c + pieces[idx].getCol() - 1 < board.getCol()) {
                if (board.isFit(pieces[idx], r, c)) {
                    board.addPiece(pieces[idx], r, c);
                    if (idx != pieces.length - 1) {
                        solveRecurse(idx + 1, 0, 0);
                    }
                    if (board.isSolved()) {
                        solved = true;
                        return;
                    }
                    board.removePiece(pieces[idx]);
                    deadend = false;
                }
                solveRecurse(idx, r, c + 1);
            }
            solveRecurse(idx, r + 1, 0);
        }
        else {
            if (pieces[idx].rotation < 3) {
                pieces[idx] = pieces[idx].rotate();
            } else if (!pieces[idx].mirror) {
                pieces[idx] = pieces[idx].rotate();
                pieces[idx] = pieces[idx].mirror();
            }
            else {
                deadend = true;
                iterations++;
                pieces[idx] = pieces[idx].reset();
                return;
            }
            solveRecurse(idx, 0, 0);
        }
    }
}