import java.util.ArrayList;

public class Piece {
    public boolean[][] matrix;
    public int num;
    public int rotation;
    public boolean mirror;
    public Piece(ArrayList<String> pieceString, int idx) {
        int row = pieceString.size();
        int col = 0;
        for (String s : pieceString) {
            if (s.length() > col) col = s.length();
        }
        matrix = new boolean[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix[i][j] = j < pieceString.get(i).length() && pieceString.get(i).charAt(j) == 'A' + (idx - 1);
            }
        }
        num = idx;
        rotation = 0;
        mirror = false;
    }
    public Piece(boolean[][] mat, int n, int r, boolean m){
        matrix = mat;
        num = n;
        rotation = r;
        mirror = m;
    }
    public static int lineCheck(String s) {
        for (int i = 0; i < s.length(); i++){
            if(s.charAt(i) >= 65 && s.charAt(i) <= 90){
                return s.charAt(i) - 'A' + 1;
            }
        }
        return -1;
    }
    public int getRow() {
        return matrix.length;
    }
    public int getCol() {
        return matrix[0].length;
    }
    public Piece rotate () {
        // rotate 90 degrees
        int r = this.getRow();
        int c = this.getCol();
        // Step 1: Transpose the matrix
        boolean[][] res = new boolean[c][r];
        for (int i = 0; i < c; i++) {
            for (int j = 0; j < r; j++) {
                // Swap matrix[i][j] with matrix[j][i]
                res[i][j] = matrix[j][i];
            }
        }

        // Step 2: Reverse each row
        for (int i = 0; i < c; i++) {
            int start = 0;
            int end = r - 1;
            while (start < end) {
                // Swap elements in the row
                boolean temp = res[i][start];
                res[i][start] = res[i][end];
                res[i][end] = temp;
                start++;
                end--;
            }
        }
        return new Piece(res, num, (rotation + 1) % 4, mirror);
    }
    public Piece mirror () {
        // mirror through the Y axis
        int r = this.getRow();
        int c = this.getCol();
        boolean[][] res = new boolean[r][c];
        for (int i = 0; i < r; i++) {
            int start = 0;
            int end = c - 1;
            while (start < end) {
                // Swap elements in the row
                res[i][start] = matrix[i][end];
                res[i][end] = matrix[i][start];
                start++;
                end--;
            }
            if (start == end) res[i][start] = matrix[i][start];
        }
        return new Piece(res, num, rotation, !mirror);
    }
    public Piece reset() { // only to be used on rotated 270 + mirrored!!
        return this.rotate().mirror();
    }
    public void printPiece() {
        int r = this.getRow();
        int c = this.getCol();
        // Step 1: Transpose the matrix
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (matrix[i][j]) System.out.print(num);
                else System.out.print(" ");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) { // rotation test
        ArrayList<String> pieceString = new ArrayList<>();
        pieceString.add("AA");
        pieceString.add("AAAA");
        pieceString.add("A");
        Piece piece = new Piece(pieceString, 1);
        piece.printPiece();
        System.out.println("Rotated 90:");
        piece = piece.rotate();
        piece.printPiece();
        System.out.println("Rotated 180:");
        piece = piece.rotate();
        piece.printPiece();
        System.out.println("Rotated 270:");
        piece = piece.rotate();
        piece.printPiece();
        System.out.println("Mirror:");
        piece = piece.rotate();
        piece = piece.mirror();
        piece.printPiece();
        System.out.println("Mirror + Rotated 90:");
        piece = piece.rotate();
        piece.printPiece();
        System.out.println("Mirror + Rotated 180:");
        piece = piece.rotate();
        piece.printPiece();
        System.out.println("Mirror + Rotated 270:");
        piece = piece.rotate();
        piece.printPiece();
    }
}
