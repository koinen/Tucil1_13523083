public class Solution {
    public long runtime;
    public long iterations;
    public Board board;
    public Piece[] pieces;
    public boolean solved;
    private boolean deadend;
    public Solution(Piece[] p, Board b) {
        iterations = 0;
        board = b;
        pieces = p;
        // Start the timer (in milliseconds)
        long startTime = System.currentTimeMillis();
        solve(0, 0, 0);
        long endTime = System.currentTimeMillis();
        runtime = endTime - startTime;
    }
    private void solve(int idx, int r, int c) {
        if (solved || deadend) {
            return;
        }
        if (r + pieces[idx].getRow() - 1 < board.getRow()) {
            if (c + pieces[idx].getCol() - 1 < board.getCol()) {
//                System.out.println("Currently on piece: " + pieces[idx].num + " on position: " + r + ", " + c);
//                pieces[idx].printPiece();
//                System.out.println();
//                System.out.println("Board:");
//                board.printBoard();
//                System.out.println();
                if (board.isFit(pieces[idx], r, c)) {
                    board.addPiece(pieces[idx], r, c);
//                    System.out.println("FIT!! Board:");
//                    board.printBoard();
//                    System.out.println();
                    if (idx != pieces.length - 1) {
                        solve(idx + 1, 0, 0);
                    }
                    if (board.isSolved()) {
                        solved = true;
                        return;
                    }
//                    System.out.println("Removing piece " + pieces[idx].num);
                    board.removePiece(pieces[idx]);
//                    System.out.println("Removed Board:");
//                    board.printBoard();
//                    System.out.println();
                    deadend = false;
                }
                solve(idx, r, c + 1);
            }
            solve(idx, r + 1, 0);
        }
        else {
            if (pieces[idx].rotation < 3) {
                pieces[idx] = pieces[idx].rotate();
            } else if (!pieces[idx].mirror) {
                pieces[idx] = pieces[idx].rotate();
                pieces[idx] = pieces[idx].mirror();
            }
            else {
//                System.out.println("Dead end for piece " + pieces[idx].num);
                deadend = true;
                iterations++;
                pieces[idx] = pieces[idx].reset();
                return;
            }
            solve(idx, 0, 0);
        }
    }
}