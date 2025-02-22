import java.util.ArrayList;

public class Board {
    public int[][] matrix;
    public Board(int row, int col) {
        matrix = new int[row][col];
    }
    public int getRow() {
        return matrix.length;
    }
    public int getCol() {
        return matrix[0].length;
    }
    public boolean isSolved(){
        for (int i = 0; i < this.getRow(); i++) {
            for (int j = 0; j < this.getCol(); j++) {
                if (matrix[i][j] == 0){
                    return false;
                }
            }
        }
        return true;
    }
    public boolean isFit(Piece piece, int r, int c) {
        // r = row number of the top-left corner of piece
        // c = column number of the top-left corner of piece
        for (int i = 0; i < piece.getRow(); i++) {
            for (int j = 0; j < piece.getCol(); j++) {
                if (r + i >= this.getRow() || c + j >= this.getCol()){
                    return false;
                }
                if (matrix[r + i][c + j] != 0 && piece.matrix[i][j]){
                    return false;
                }
            }
        }
        return true;
    }
    public void addPiece(Piece piece, int r, int c) {
        for (int i = 0; i < piece.getRow(); i++) {
            for (int j = 0; j < piece.getCol(); j++) {
                if (piece.matrix[i][j]) matrix[r + i][c + j] = piece.num;
            }
        }
    }
    private void removePieceHelper(int r, int c) {
        int toRemove = matrix[r][c];
        matrix[r][c] = 0;
        if (r < this.getRow() - 1 && matrix[r + 1][c] == toRemove) {removePieceHelper(r + 1, c); }
        if (r > 0 && matrix[r - 1][c] == toRemove) {removePieceHelper(r - 1, c); }
        if (c < this.getCol() - 1 && matrix[r][c + 1] == toRemove) {removePieceHelper(r, c + 1); }
        if (c > 0 && matrix[r][c - 1] == toRemove) {removePieceHelper(r, c - 1); }
    }
    public void removePiece(Piece piece) {
        for (int i = 0; i < this.getRow(); i++) {
            for (int j = 0; j < this.getCol(); j++) {
                if(matrix[i][j] == piece.num){
                    removePieceHelper(i, j);
                }
            }
        }
    }
    public void printBoard() {
        IO helper = new IO();
        for (int i = 0; i < this.getRow(); i++) {
            for (int j = 0; j < this.getCol(); j++) {
                char toPrint = (char) ('A' + (matrix[i][j] - 1));
                helper.printColor(toPrint, matrix[i][j] - 1);
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        Board board = new Board(5, 5);
        Piece[] pieces = new Piece[2];
        ArrayList<String> pieceString = new ArrayList<>();
        pieceString.add("A");
        pieceString.add("AA");
        pieces[0] = new Piece(pieceString);
        pieceString = new ArrayList<>();
        pieceString.add("B");
        pieceString.add("BBBB");
        pieces[1] = new Piece(pieceString);
        // Test isFit
        System.out.println(board.isFit(pieces[0], 0, 0)); // true
        System.out.println(board.isFit(pieces[0], 0, 1)); // true
        // Test addPiece
        System.out.println("Adding piece 1 to (0, 0)");
        board.addPiece(pieces[0], 0, 0);
        board.printBoard();
        if (board.isFit(pieces[0], 1, 0)) {
            System.out.println("Adding piece 2 to (0, 2)");
            board.addPiece(pieces[1], 1, 0);
            board.printBoard();
        } else {
            System.out.println("Piece 2 does not fit");
        }
        // Test isFit

        System.out.println(board.isFit(pieces[1], 0, 2)); // false
        // Test removePiece
        System.out.println("Removing piece 1");
        board.removePiece(pieces[0]);
        board.printBoard();
        System.out.println("Removing piece 2");
        board.removePiece(pieces[1]);
        board.printBoard();
    }
}